# JIBLT
Java Image Batch Layering Tool

This command line tool automatically layers a large amount of images together, it was originally designed to be used for Dead by Daylight perk icon pack creation, but it can be used to do other things as well.

JIBLT is bundled with Google Gson, this is for configuration because I hate using Java IO to write/read files.<br>
**You will require an installation of Java 8+ for this program to work.**

The setup and usage of the program is simple:

1. First, open up command prompt or any other terminal tool you have.
2. Navigate to the directory of which JIBLT is stored in (I recommend putting it in it's own folder, it creates a lot of files) using the command `cd <file path>` (replace <file path> with the actual file path) or whatever the equivalent is for your OS.
3. To setup your main layer (the middle layer), type the command `java -jar <JIBLTs file name> setmainlayer <file/directory>` You can select either a single file or an entire directory, no files will be harmed in the process as it will only create new ones, not remove the old ones (you will have to do that manually)
4. To add layers, type the command `java -jar <JIBLTs file name> [addabove/addbelow] <file name>.png`, this program will only accept PNGs as those are transparent and have full color depth, only type either `addabove` or `addbelow`, addabove will add a new layer above the main layer, and add below will do the opposite, adding a new layer below the main layer.
5. Done!

