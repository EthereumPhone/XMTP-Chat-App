# Read in the file
with open('index.js', 'r') as file :
  filedata = file.read()

# Replace the target string
filedata = filedata.replace('ram', 'abcd')

# Write the file out again
with open('index.js', 'w') as file:
  file.write(filedata)
