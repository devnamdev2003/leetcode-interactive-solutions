import json
import os
import re

def parse_tags(tags_input):
    """
    Splits squished tags like 'ArrayHash Table' into ['Array', 'Hash Table'].
    It inserts a comma between a lowercase letter and an uppercase letter.
    """
    split_tags = re.sub(r'(?<=[a-z])(?=[A-Z])', ',', tags_input)
    # Split by comma and clean up whitespace
    return [tag.strip() for tag in split_tags.split(',') if tag.strip()]

def extract_slug(url):
    """Extracts the problem slug from the LeetCode URL."""
    parts = url.strip('/').split('/')
    if 'problems' in parts:
        idx = parts.index('problems')
        if idx + 1 < len(parts):
            return parts[idx + 1]
    return "unknown-problem"

def main():
    print("--- Enter LeetCode Data ---")
    leetcode_url = input("leetcode url: ").strip()
    name_input = input("name: ").strip()
    difficulty = input("difficulty: ").strip()
    
    # --- MULTI-LINE TAG INPUT ---
    print("tags (paste your tags, then press Enter twice to finish):")
    tags_list = []
    while True:
        line = input()
        # If the user presses Enter without typing anything, stop reading
        if not line.strip(): 
            break
        
        # Process the line through our parser just in case it is squished,
        # and add the results to our master tags list.
        tags_list.extend(parse_tags(line))

    # 1. Parse ID and Title
    if "." in name_input:
        id_part, title_part = name_input.split(".", 1)
        prob_id = id_part.strip()
        title = title_part.strip()
    else:
        prob_id = "Unknown"
        title = name_input

    # 2. Construct algovizUrl dynamically
    slug = extract_slug(leetcode_url)
    if tags_list:
        first_tag_folder = tags_list[0].lower().replace(" ", "-") 
    else:
        first_tag_folder = "unknown"
        
    algoviz_url = f"{first_tag_folder}/{slug}.html"

    # 3. Create the JSON object
    new_entry = {
        "id": prob_id,
        "title": title,
        "difficulty": difficulty,
        "tags": tags_list,
        "leetcodeUrl": leetcode_url,
        "algovizUrl": algoviz_url
    }

    # 4. Append to data.json
    file_name = "leetcode/data.json"
    
    if os.path.exists(file_name):
        try:
            with open(file_name, "r") as file:
                data = json.load(file)
        except json.JSONDecodeError:
            data = [] 
    else:
        data = []

    if not isinstance(data, list):
        data = [data]

    data.append(new_entry)

    with open(file_name, "w") as file:
        json.dump(data, file, indent=4)
    
    print(f"\n✅ Successfully appended '{title}' to {file_name}!")

if __name__ == "__main__":
    main()