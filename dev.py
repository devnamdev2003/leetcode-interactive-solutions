import json
import os
import re


def parse_tags(tags_input):
    """
    Splits squished tags like 'ArrayHash Table'
    into ['Array', 'Hash Table']
    """
    split_tags = re.sub(r"(?<=[a-z])(?=[A-Z])", ",", tags_input)
    return [tag.strip() for tag in split_tags.split(",") if tag.strip()]


def extract_slug(url):
    """
    Extracts the problem slug from the URL.
    Example:
    https://leetcode.com/problems/container-with-most-water/
    -> container-with-most-water
    """
    parts = url.strip("/").split("/")

    if "problems" in parts:
        idx = parts.index("problems")
        if idx + 1 < len(parts):
            return parts[idx + 1]

    return "unknown-problem"


def get_platform(url):
    """
    Detect platform from URL.
    """
    url = url.lower()

    if "leetcode.com" in url:
        return "LeetCode"
    elif "geeksforgeeks.org" in url:
        return "GeeksForGeeks"
    elif url == "#":
        return "Custom"

    return "Custom"


def main():
    print("--- Enter Problem Data ---")

    problem_url = input("Problem URL: ").strip()
    name_input = input("Name (e.g. 11. Container With Most Water): ").strip()
    difficulty = input("Difficulty: ").strip()

    print("\nTags (paste tags, press Enter twice to finish):")

    tags_list = []

    while True:
        line = input()

        if not line.strip():
            break

        tags_list.extend(parse_tags(line))

    # Parse ID and Title
    if "." in name_input:
        id_part, title_part = name_input.split(".", 1)

        try:
            prob_id = int(id_part.strip())
        except ValueError:
            prob_id = id_part.strip()

        title = title_part.strip()
    else:
        prob_id = "Unknown"
        title = name_input

    # Generate default paths
    slug = extract_slug(problem_url)

    if tags_list:
        folder = tags_list[0].lower().replace(" ", "-")
    else:
        folder = "unknown"

    default_brute = f"{folder}/{slug}-brute.html"
    default_better = f"{folder}/{slug}-better.html"
    default_optimal = f"{folder}/{slug}.html"

    print("\n--- Solution URLs ---")
    print("Press Enter to use the default value.")
    print()

    brute_url = input(f"Brute Force URL [{default_brute}]: ").strip()
    better_url = input(f"Better URL [{default_better}]: ").strip()
    optimal_url = input(f"Optimal URL [{default_optimal}]: ").strip()

    solutions = []

    if brute_url:
        solutions.append({"name": "Brute Force", "url": brute_url})

    if better_url:
        solutions.append({"name": "Better", "url": better_url})

    if optimal_url:
        solutions.append({"name": "Optimal", "url": optimal_url})

    # If user pressed Enter, use defaults
    if not brute_url and not better_url and not optimal_url:
        solutions.append({"name": "Optimal", "url": default_optimal})

    new_entry = {
        "id": prob_id,
        "title": title,
        "difficulty": difficulty,
        "tags": tags_list,
        "platform": get_platform(problem_url),
        "platformUrl": problem_url,
        "solutions": solutions,
    }

    file_name = "leetcode/data.json"

    if os.path.exists(file_name):
        try:
            with open(file_name, "r", encoding="utf-8") as file:
                data = json.load(file)
        except json.JSONDecodeError:
            data = []
    else:
        data = []

    if not isinstance(data, list):
        data = [data]

    data.append(new_entry)

    with open(file_name, "w", encoding="utf-8") as file:
        json.dump(data, file, indent=4)

    print(f"\n✅ Successfully appended '{title}' to {file_name}!")

    print("\nGenerated Entry:")
    print(json.dumps(new_entry, indent=4))


if __name__ == "__main__":
    main()
