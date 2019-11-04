# dblp-explorer
cosc4353 hw5  

Here are two modes for my program: Slow mode and Fast mode  

 For the fast mode, I didn't output every information about the paper,
 because if the program sorts the whole json file in memory, it will cost large than 10 GB. 
 So I just output the titles of papers in order of the importance. This mode is fast but so memory costly
 because it sorts the data in the memory.  
 
 For the slow mode, I split the files in several small files,
  then sorts the small file and finally merge one by one, hence it won't cost lots of memory.
  But the process is really slow. And also writing data to output file requires a long time.
 All output file is in the directory "temp_files".  
 The final output file is "tempfile_fastmode" and "the last sortedfile"
 
 Here is a shortage of my program, I have no idea to store the field "indexed-abstract" of the json file each line into
 java object. So I removed this kind of information for the outputs.