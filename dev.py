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

# Add at the beginning
data.insert(0, new_entry)

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
            f.write("""
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


""")

        print(f"Created: {file_path}")

    else:
        print(f"Already exists: {file_path}")

print("Done!")