import json
from pathlib import Path

# Path to the json-data folder
json_folder = Path("practice\\json-data")

# Manifest file path
manifest_file = json_folder / "manifest.json"

# Get all JSON files except manifest.json
json_files = sorted(
    [file.name for file in json_folder.glob("*.json") if file.name != "manifest.json"]
)

# Write to manifest.json
with open(manifest_file, "w", encoding="utf-8") as f:
    json.dump(json_files, f, indent=4)

print(f"Manifest created with {len(json_files)} files.")
