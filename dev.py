import json
import os

DATA_FILE = "leetcode/data.json"

print("Paste JSON and press Enter twice:\n")

json_lines = []

while True:
    line = input()
    if not line.strip():
        break
    json_lines.append(line)

json_text = "\n".join(json_lines)

try:
    new_entry = json.loads(json_text)
except json.JSONDecodeError as e:
    print(f"Invalid JSON: {e}")
    exit()

# Load existing data
try:
    with open(DATA_FILE, "r", encoding="utf-8") as f:
        data = json.load(f)
except (FileNotFoundError, json.JSONDecodeError):
    data = []

# Check duplicate ID
new_id = new_entry["id"]

if any(item["id"] == new_id for item in data):
    print(f"ID {new_id} already exists.")
    exit()

# Add at the end
data.append(new_entry)

# Save
with open(DATA_FILE, "w", encoding="utf-8") as f:
    json.dump(data, f, indent=2)

print(f"Added problem #{new_id}")
# Create solution files
for solution in new_entry["solutions"]:

    file_path = os.path.join("leetcode", solution["url"])

    os.makedirs(os.path.dirname(file_path), exist_ok=True)

    if not os.path.exists(file_path):

        with open(file_path, "w", encoding="utf-8") as f:
            f.write(f"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{new_entry['title']} - {solution['name']}</title>
</head>
<body>

</body>
</html>
""")

        print(f"Created: {file_path}")

    else:
        print(f"Already exists: {file_path}")

print("Done!")