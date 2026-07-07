import requests
import json
import re
import os


# --- Terminal Styling Helpers ---
class Colors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


def print_banner():
    """Prints a modern, attractive banner for the CLI."""
    print(f"\n{Colors.OKCYAN}{Colors.BOLD}╔════════════════════════════════════════════════════╗{Colors.ENDC}")
    print(f"{Colors.OKCYAN}{Colors.BOLD}║            🚀 LeetCode Scraper Pro 🚀            ║{Colors.ENDC}")
    print(f"{Colors.OKCYAN}{Colors.BOLD}╚════════════════════════════════════════════════════╝{Colors.ENDC}\n")


def fetch_leetcode_data(url):
    """
    Takes a LeetCode problem URL, queries the LeetCode GraphQL API,
    and returns the title slug and raw question data.
    """
    # 1. Extract the title slug from the URL
    match = re.search(r"leetcode\.com/problems/([^/]+)", url)
    if not match:
        raise ValueError(
            "Invalid LeetCode URL provided. Make sure it contains '/problems/<problem-name>'"
        )

    title_slug = match.group(1)

    # 2. Setup the LeetCode GraphQL API request
    graphql_url = "https://leetcode.com/graphql"

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

    return title_slug, question_data


def format_problem_data(url, title_slug, question_data, folder_to_use, selected_solutions):
    """
    Formats the raw GraphQL data into the final dictionary structure,
    only including the solution files the user requested.
    """
    tags = [tag["name"] for tag in question_data.get("topicTags", [])]

    # Map the solution types to their respective file name suffixes
    solution_configs = {
        "Brute": {"name": "Brute", "suffix": "-brute.html"},
        "Better": {"name": "Better", "suffix": "-better.html"},
        "Optimal": {"name": "Optimal", "suffix": ".html"}
    }

    solutions = []
    # We iterate over this specific list to ensure the logical order is preserved 
    # (Brute -> Better -> Optimal) regardless of how the user typed it.
    for key in ["Brute", "Better", "Optimal"]:
        if key in selected_solutions:
            solutions.append({
                "name": solution_configs[key]["name"],
                "url": f"{folder_to_use}/{title_slug}{solution_configs[key]['suffix']}"
            })

    result = {
        "id": question_data["questionId"],
        "title": question_data["title"],
        "difficulty": question_data["difficulty"],
        "tags": tags,
        "platform": "LeetCode",
        "platformUrl": url,
        "solutions": solutions,
    }

    return result


def save_to_json_file(new_data, filename="data.json"):
    """
    If the problem ID already exists, update it.
    Otherwise, insert the new problem at the top.
    """
    existing_data = []

    if os.path.exists(filename):
        try:
            with open(filename, "r", encoding="utf-8") as f:
                content = f.read()
                if content.strip():
                    existing_data = json.loads(content)
                    if not isinstance(existing_data, list):
                        existing_data = [existing_data]
        except json.JSONDecodeError:
            print(
                f"{Colors.WARNING}⚠️ Warning: {filename} was corrupted. Creating a new file.{Colors.ENDC}"
            )
            existing_data = []

    updated = False

    # Check whether ID already exists
    for i, problem in enumerate(existing_data):
        if str(problem.get("id")) == str(new_data["id"]):
            existing_data[i] = new_data
            updated = True

            print(
                f"{Colors.WARNING}⚠️ Problem ID {new_data['id']} already exists.{Colors.ENDC}"
            )
            print(
                f"{Colors.OKCYAN}🔄 Updating existing problem...{Colors.ENDC}"
            )
            break

    # Insert at top if it doesn't exist
    if not updated:
        existing_data.insert(0, new_data)
        print(
            f"{Colors.OKGREEN}➕ New problem added.{Colors.ENDC}"
        )

    # Ensure directory exists
    os.makedirs(
        os.path.dirname(filename) if os.path.dirname(filename) else ".",
        exist_ok=True,
    )

    with open(filename, "w", encoding="utf-8") as f:
        json.dump(existing_data, f, indent=2)

    if updated:
        print(
            f"{Colors.OKGREEN}✅ Existing entry updated successfully!{Colors.ENDC}"
        )
    else:
        print(
            f"{Colors.OKGREEN}✅ Data added successfully!{Colors.ENDC}"
        )

def create_solution_files(solutions, problem_title):
    """
    Creates the physical directories and HTML files based on the solution URLs.
    """
    for sol in solutions:
        filepath = os.path.join("leetcode", sol["url"])
        directory = os.path.dirname(filepath)

        if directory:
            os.makedirs(directory, exist_ok=True)

        if not os.path.exists(filepath):
            with open(filepath, "w", encoding="utf-8") as f:
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
        <!-- paste your code here --> 
        </code></pre>
    </div>

    <script>
      const codeBlock = document.getElementById("code-block");
      const copyBtn = document.querySelector(".copy-btn");

      const hasCode = codeBlock.textContent.trim() !== "";

      if (!hasCode) {
        codeBlock.textContent = `
╔══════════════════════════════╗
║        🚧 COMING SOON 🚧      ║
╚══════════════════════════════╝

The code solution will be available soon.
Stay tuned!
`;

        codeBlock.classList.remove("language-java");
      }

      copyBtn.style.display = hasCode ? "block" : "none";

      function copyCode() {
        navigator.clipboard.writeText(codeBlock.textContent).then(() => {
          copyBtn.textContent = "Copied!";
          setTimeout(() => {
            copyBtn.textContent = "Copy";
          }, 2000);
        });
      }
    </script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-java.min.js"></script>
  </body>
</html>
"""
                f.write(html_content.strip())
            print(f"   {Colors.OKGREEN}📄 Created:{Colors.ENDC} {filepath}")
        else:
            print(f"   {Colors.WARNING}⏭️  Skipped (Already exists):{Colors.ENDC} {filepath}")


if __name__ == "__main__":
    print_banner()
    
    user_url = input(f"{Colors.OKCYAN}[?] Enter the LeetCode problem URL:{Colors.ENDC} ").strip()

    if user_url:
        try:
            print(f"\n{Colors.OKBLUE}⏳ Fetching data from LeetCode...{Colors.ENDC}")

            # 1. Fetch raw data
            title_slug, question_data = fetch_leetcode_data(user_url)
            
            # Display Problem Info
            print(f"\n{Colors.HEADER}✨ Problem:{Colors.ENDC} {Colors.BOLD}{question_data['title']}{Colors.ENDC}")
            diff_color = Colors.OKGREEN if question_data['difficulty'] == "Easy" else (Colors.WARNING if question_data['difficulty'] == "Medium" else Colors.FAIL)
            print(f"{Colors.HEADER}📊 Difficulty:{Colors.ENDC} {diff_color}{question_data['difficulty']}{Colors.ENDC}")

            # 2. Extract tags & Prompt for Folder
            tags_data = question_data.get("topicTags", [])
            if not tags_data:
                tags_data = [{"name": "Miscellaneous", "slug": "misc"}]

            print(f"\n{Colors.OKCYAN}🏷️  Available Folder Tags:{Colors.ENDC}")
            for i, tag in enumerate(tags_data, 1):
                print(f"   {Colors.BOLD}[{i}]{Colors.ENDC} {tag['slug']}")

            print(f"\n{Colors.OKBLUE}💡 Options:{Colors.ENDC}")
            print(f"   - Enter a {Colors.BOLD}number{Colors.ENDC} to select a tag from the list.")
            print(f"   - Type a {Colors.BOLD}custom name{Colors.ENDC} to create a specific folder.")
            print(f"   - Press {Colors.BOLD}Enter{Colors.ENDC} to use the default [{Colors.BOLD}{tags_data[0]['slug']}{Colors.ENDC}].")

            user_choice = input(f"\n{Colors.OKGREEN}[?] Your choice:{Colors.ENDC} ").strip()
            
            # Determine which folder string to use
            folder_to_use = tags_data[0]['slug'] # Default
            if user_choice.isdigit() and 1 <= int(user_choice) <= len(tags_data):
                folder_to_use = tags_data[int(user_choice) - 1]['slug']
            elif user_choice != "":
                folder_to_use = user_choice

            print(f"\n{Colors.OKGREEN}📂 Target Folder set to:{Colors.ENDC} {Colors.BOLD}{folder_to_use}{Colors.ENDC}")


            # 3. Prompt for Solution Files to Create
            print(f"\n{Colors.OKCYAN}📄 Which solution files do you want to create?{Colors.ENDC}")
            print(f"   {Colors.BOLD}[1]{Colors.ENDC} Brute")
            print(f"   {Colors.BOLD}[2]{Colors.ENDC} Better")
            print(f"   {Colors.BOLD}[3]{Colors.ENDC} Optimal")
            
            print(f"\n{Colors.OKBLUE}💡 Options:{Colors.ENDC}")
            print(f"   - Enter {Colors.BOLD}comma-separated numbers{Colors.ENDC} (e.g., '1,3' for Brute and Optimal).")
            print(f"   - Enter {Colors.BOLD}'3'{Colors.ENDC} for Optimal only.")
            print(f"   - Press {Colors.BOLD}Enter{Colors.ENDC} to create ALL three (Default).")

            file_choice = input(f"\n{Colors.OKGREEN}[?] Your choice:{Colors.ENDC} ").strip()
            
            solution_map = {"1": "Brute", "2": "Better", "3": "Optimal"}
            selected_solutions = []

            # Parse user input for files
            if not file_choice:
                 selected_solutions = ["Brute", "Better", "Optimal"]
            else:
                 parts = file_choice.split(',')
                 for p in parts:
                     p = p.strip()
                     if p in solution_map and solution_map[p] not in selected_solutions:
                         selected_solutions.append(solution_map[p])

            if not selected_solutions:
                print(f"   {Colors.WARNING}⚠️ Invalid choice. Defaulting to ALL.{Colors.ENDC}")
                selected_solutions = ["Brute", "Better", "Optimal"]

            print(f"\n{Colors.OKGREEN}🛠️  Files to generate:{Colors.ENDC} {Colors.BOLD}{', '.join(selected_solutions)}{Colors.ENDC}")

            # 4. Format the final dictionary
            problem_data = format_problem_data(user_url, title_slug, question_data, folder_to_use, selected_solutions)

            # 5. Save it to data.json
            print(f"\n{Colors.OKBLUE}⏳ Updating JSON data...{Colors.ENDC}")
            save_to_json_file(problem_data, "leetcode/data.json")

            # 6. Create the corresponding HTML files and directories
            print(f"\n{Colors.OKBLUE}⏳ Generating solution files...{Colors.ENDC}")
            create_solution_files(problem_data["solutions"], problem_data["title"])
            
            print(f"\n{Colors.OKGREEN}{Colors.BOLD}🎉 All tasks completed successfully!{Colors.ENDC}\n")

        except Exception as e:
            print(f"\n{Colors.FAIL}❌ An error occurred:{Colors.ENDC} {e}\n")
    else:
        print(f"\n{Colors.WARNING}⚠️  No URL provided. Exiting.{Colors.ENDC}\n")