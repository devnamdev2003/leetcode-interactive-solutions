import requests
import json
import re
import os


def scrape_leetcode_problem(url):
    """
    Takes a LeetCode problem URL, queries the LeetCode GraphQL API,
    and returns a python dictionary with the formatted data.
    """

    # 1. Extract the title slug from the URL
    # e.g., https://leetcode.com/problems/combination-sum-ii/ -> combination-sum-ii
    match = re.search(r"leetcode\.com/problems/([^/]+)", url)
    if not match:
        raise ValueError(
            "Invalid LeetCode URL provided. Make sure it contains '/problems/<problem-name>'"
        )

    title_slug = match.group(1)

    # 2. Setup the LeetCode GraphQL API request
    graphql_url = "https://leetcode.com/graphql"

    # This query asks specifically for the data we need to build your JSON
    query = """
    query questionData($titleSlug: String!) {
      question(titleSlug: $titleSlug) {
        questionId
        title
        difficulty
        topicTags {
          name
          slug
        }
      }
    }
    """

    variables = {"titleSlug": title_slug}

    # Adding a standard User-Agent to avoid basic blocking
    headers = {
        "Content-Type": "application/json",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
    }

    # 3. Fetch the data
    response = requests.post(
        graphql_url, json={"query": query, "variables": variables}, headers=headers
    )

    if response.status_code != 200:
        raise Exception(f"Failed to fetch data: HTTP {response.status_code}")

    data = response.json()

    if "errors" in data or not data.get("data", {}).get("question"):
        raise Exception(
            "Failed to fetch question data. The problem might be premium-locked or the URL is incorrect."
        )

    question_data = data["data"]["question"]

    # 4. Process Tags
    # Extract the display names for the tags array (e.g., ["Array", "Backtracking"])
    tags = [tag["name"] for tag in question_data["topicTags"]]

    # Get the slugified version of the first tag for the URL generation (e.g., "Array" -> "array")
    # If a problem has no tags, fallback to "misc"
    first_tag_folder = (
        question_data["topicTags"][0]["slug"] if question_data["topicTags"] else "misc"
    )

    # 5. Generate Solution Objects automatically
    solutions = [
        {"name": "Brute", "url": f"{first_tag_folder}/{title_slug}-brute.html"},
        {"name": "Better", "url": f"{first_tag_folder}/{title_slug}-better.html"},
        {"name": "Optimal", "url": f"{first_tag_folder}/{title_slug}.html"},
    ]

    # 6. Construct final dictionary
    result = {
        "id": question_data["questionId"],
        "title": question_data["title"],
        "difficulty": question_data["difficulty"],
        "tags": tags,
        "platform": "LeetCode",
        "platformUrl": url,
        "solutions": solutions,
    }

    # Return as a dictionary so it can be easily appended to a JSON list
    return result


def save_to_json_file(new_data, filename="data.json"):
    """
    Reads existing JSON data (if any), inserts the new data at the top,
    and writes it back to the file.
    """
    existing_data = []

    # Check if file exists and load existing data
    if os.path.exists(filename):
        try:
            with open(filename, "r") as f:
                content = f.read()
                if content.strip():
                    existing_data = json.loads(content)
                    # Ensure the data is a list
                    if not isinstance(existing_data, list):
                        existing_data = [existing_data]
        except json.JSONDecodeError:
            print(f"Warning: {filename} was corrupted or empty. Creating a new list.")
            existing_data = []

    # Insert new data at the top (index 0)
    existing_data.insert(0, new_data)

    # Write everything back to the file
    with open(filename, "w") as f:
        json.dump(existing_data, f, indent=2)

    print(f"\nSuccess! Data added to the top of {filename}")


def create_solution_files(solutions, problem_title):
    """
    Creates the physical directories and HTML files based on the solution URLs.
    """
    for sol in solutions:
        filepath = os.path.join("leetcode", sol["url"])
        # Extract the directory name from the path (e.g., "array" from "array/combination-sum-ii.html")
        directory = os.path.dirname(filepath)

        # Create the directory if it doesn't exist
        if directory:
            os.makedirs(directory, exist_ok=True)

        # Create the file if it doesn't already exist
        if not os.path.exists(filepath):
            with open(filepath, "w", encoding="utf-8") as f:
                # Adding a basic HTML boilerplate so the file isn't completely empty
                html_content = """
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Code Viewer</title>

    <!-- Prism Theme -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-tomorrow.min.css"
    />

    <style>
      body {
        background: #1e1e1e;
        color: #d4d4d4;
        font-family: Consolas, Monaco, monospace;
        padding: 20px;
      }

      .code-container {
        position: relative;
        max-width: 1200px;
        margin: auto;
      }

      .copy-btn {
        position: absolute;
        top: 12px;
        right: 12px;
        background: #0078d4;
        color: white;
        border: none;
        padding: 8px 14px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
        transition: 0.2s;
      }

      .copy-btn:hover {
        background: #106ebe;
      }

      pre {
        border-radius: 10px;
        padding: 20px;
        overflow-x: auto;
        font-size: 15px;
        line-height: 1.6;
      }
    </style>
  </head>

  <body>
    <div class="code-container">
      <button class="copy-btn" onclick="copyCode()">Copy</button>

      <pre><code id="code-block" class="language-java">

        </code></pre>
    </div>

    <script>
      function copyCode() {
        const code = document.getElementById("code-block").textContent;

        navigator.clipboard.writeText(code).then(() => {
          const btn = document.querySelector(".copy-btn");

          btn.textContent = "Copied!";
          setTimeout(() => {
            btn.textContent = "Copy";
          }, 2000);
        });
      }
    </script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-java.min.js"></script>
  </body>
</html>

"""
                f.write(html_content)
            print(f"Created file: {filepath}")
        else:
            print(f"File already exists: {filepath}")


if __name__ == "__main__":
    # Take URL directly from the terminal
    user_url = input("Enter the LeetCode problem URL: ").strip()

    if user_url:
        try:
            print(f"\nScraping data for: {user_url}")

            # 1. Get the data dictionary
            problem_data = scrape_leetcode_problem(user_url)

            # 2. Print it to the terminal for visual confirmation
            print("\nExtracted Data:")
            print(json.dumps(problem_data, indent=2))

            # 3. Save it to data.json
            save_to_json_file(problem_data, "leetcode/data.json")

            # 4. Create the corresponding HTML files and directories
            print("\nGenerating solution files...")
            create_solution_files(problem_data["solutions"], problem_data["title"])

        except Exception as e:
            print(f"\nAn error occurred: {e}")
    else:
        print("No URL provided. Exiting.")
