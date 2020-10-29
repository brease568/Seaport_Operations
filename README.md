# Seaport Operations
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/brease568/Seaport_Operations) ![Lines of code](https://img.shields.io/tokei/lines/github/brease568/Seaport_Operations)

The Seaport Operations program is a project based on understanding Object Oriented Design (OOD) and multi-threading and concurrency. The program will simulate aspects of a number of seaports. The project reads a data file and creates a HashMap data structure based on the contents. The structure is displayed to the user through the GUI. The user will be able to sort the structure and watch the progress of jobs being completed at the seaport. Each job is run on its own thread and must wait for limited resources (persons with particular skills) to complete jobs on a ship. 

## Prerequisites

- Java version 8+

## Usage

1. Launch the program. A GUI will be presented to you:

![ScreenShot](/images/default_gui.PNG)

2. Click the 'Choose File' button and select a properly formatted data file. Examples of the data file are in the /data/ directory.
3. Click the 'Read File' button. This will direct the program to read the data file you chose and perform several actions:
  - Display the 'World' as a clickable drop-down.
  - Output a summary of the world and its contents in a summary window.
  - Display jobs and a progress bar showing the job completion status.
  - Output the available persons to complete jobs and their status as a resource pool.
  - Allow the user to search and sort objects in the 'World'.

![ScreenShot](/images/read_file.PNG)

## Example Output

- Display the 'World' as a clickable drop-down:

![ScreenShot](/images/world_navigation.PNG)

- Output a summary of the world and its contents in a summary window:

![ScreenShot](/images/world_summary.PNG)

- Display jobs and a progress bar showing the job completion status:

![ScreenShot](/images/job_progress.PNG)

- Output the available persons to complete jobs and their status as a resource pool:

![ScreenShot](/images/resource_pool.PNG)

- Allow the user to search and sort objects in the 'World':

![ScreenShot](/images/search_or_sort.PNG)


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
