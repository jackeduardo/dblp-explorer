import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static void main(String args[]) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("1. slow mode (consuming less memory)");
        System.out.println("2. fast mode (consuming large memory)");
        System.out.print("Enter the number: ");
        long startTime = System.currentTimeMillis();

        int num = input.nextInt();
        if(num==1){// For the slow mode, I split the files in several small files, then sorts the small file and finally merge one by one, hence it won't cost lots of memory.
                    // But the process is really slow.
            System.out.println("1. Rank by id");
            System.out.println("2. Rank by Title");
            System.out.println("3. Rank by cited times");
            int pick=input.nextInt();
            List<File> filelist = new ArrayList<>();
            read_write_blocks("C:\\Users\\xuhaiyang\\Desktop\\cosc4353\\dblp.v11\\dblp_papers_v11.txt", filelist,pick);
            int index=0;
            for (int i = 0; i < filelist.size() - 1; i++) {
                merge(filelist.get(i), filelist.get(i + 1), i,pick);
                index=i;
            }
            File dir=new File(System.getProperty("user.dir") + "\\temp_files\\");
            removefiles(dir,index);
        }
        else if(num==2){//For the fast mode, I didn't output every information about the paper,
            // because if the program sorts the whole json file in memory, It will cost large than 10 GB. So I just output the titles of papers in order of the importance.
        System.out.println("1. Rank by id");
        System.out.println("2. Rank by Title");
        System.out.println("3. Rank by cited times");
        int pick=input.nextInt();
        largeFileIO("C:\\Users\\xuhaiyang\\Desktop\\cosc4353\\dblp.v11\\dblp_papers_v11.txt", "output", pick);}
        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) + "ms");

    }

    public static void largeFileIO(String inputFile, String outputFile, int pick) throws IOException {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);
            //List<Dblp_Model> sorted_papers = new ArrayList<>();
            List sorted_papers = new ArrayList<>();
            List<String> title=new ArrayList<>();
            if (pick == 1) {
                sorted_papers = new ArrayList<Integer>();
            } else if (pick == 2) {
                sorted_papers = new ArrayList<String>();
            } else {
                sorted_papers = new ArrayList<Integer>();
            }
            Gson gson = new Gson();
            List<String> papers_title = new ArrayList<>();
            while (in.ready()) {
                String line = in.readLine();
                Dblp_Model paper = gson.fromJson(line, Dblp_Model.class);
                //sorted_papers.add(paper);
                if (pick == 1){
                    sorted_papers.add(paper.getId());
                    title.add(paper.getTitle());
                }
                else if (pick == 2) {
                    sorted_papers.add(paper.getTitle());
                    title.add(paper.getTitle());
                } else {
                    sorted_papers.add(paper.getreference_length());
                    title.add(paper.getTitle());
                }
            }
            in.close();
            List Sorted= (List) sorted_papers.stream().sorted().collect(Collectors.toList());
            File outfile = new File(System.getProperty("user.dir") + "\\temp_files\\tempfile_fastmode");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile, true), "UTF-8"), 2*10 * 1024);
            int count=0;
            for(int i=0;i<Sorted.size();i++){
                if(pick==1){
                    out.write("Title is: "+title.get(i)+"   | ID is: "+Sorted.get(i));
                    out.newLine();
                    count++;}
                else if(pick==2){
                    out.write("Title is: "+Sorted.get(i));
                    out.newLine();
                    count++;
                }
                else{
                    out.write("Title is: "+title.get(i)+"   | Cited times is: "+Sorted.get(i));
                    out.newLine();
                    count++;
                }
            }
            out.close();
            System.out.println(count);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void read_write_blocks(String filename, List<File> fileList,int pick) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter out = null;
        File tempfile = null;

        try {
            String line;
            //FileWriter fw=null;
            int line_counter = 0;
            int files_counter = 1;
            int total = 0;
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                System.out.println(line_counter++);
                total++;
                if (line_counter >= 600000) {
                    line_counter = 0;
                    sorting_blocks(tempfile, files_counter, fileList,pick);
                    System.out.println(files_counter++);

                } else {
                    tempfile = new File(System.getProperty("user.dir") + "\\temp_files\\tempfile" + files_counter);
                    out = new BufferedWriter(new FileWriter(tempfile,true), 32768);
                     //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfile, true), "UTF-8"), 3*10 * 1024);
                    out.write(line);
                    out.newLine();
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sorting_blocks(File tempfile, int filecounter, List<File> filelist,int pick) throws IOException {
        FileReader fileReader = new FileReader(tempfile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter out = null;
        try {
            String line;
            //FileWriter fw=null;
            Gson gson = new Gson();
            int line_counter = 0;
            List<Dblp_Model> papers = new ArrayList<>();
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                Dblp_Model paper = gson.fromJson(line, Dblp_Model.class);
                papers.add(paper);
            }
            File tempfile_sorted = new File(System.getProperty("user.dir") + "\\temp_files\\tempfilesorted" + filecounter);
            filelist.add(tempfile_sorted);
            if(pick==1){
            papers.stream().sorted(Comparator.comparing(Dblp_Model::getId)).collect(Collectors.toList()).forEach(dblp_model -> {
                try {
                    writetemp_file(dblp_model, tempfile_sorted);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });}
            else if(pick==2){
                papers.stream().sorted(Comparator.comparing(Dblp_Model::getTitle)).collect(Collectors.toList()).forEach(dblp_model -> {
                    try {
                        writetemp_file(dblp_model, tempfile_sorted);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            else{
                papers.stream().sorted(Comparator.comparing(Dblp_Model::getreference_length)).collect(Collectors.toList()).forEach(dblp_model -> {
                    try {
                        writetemp_file(dblp_model, tempfile_sorted);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writetemp_file(Dblp_Model papers, File tempfilesorted) throws IOException {
        Gson gson = new Gson();
        BufferedWriter out = null;
        String line = gson.toJson(papers);
        out = new BufferedWriter(new FileWriter(tempfilesorted,true), 32768);
        //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempfilesorted, true), "UTF-8"), 3*10 * 1024);
        out.write(line);
        out.newLine();
        out.flush();
        out.close();
    }

    private static void merge(File left, File right, int filecounter,int pick) throws IOException {
        FileReader inputStream_left = new FileReader(left);
        BufferedReader in_left = new BufferedReader(inputStream_left);
        FileReader inputStream_right = new FileReader(left);
        BufferedReader in_right = new BufferedReader(inputStream_left);
//        BufferedInputStream inputStream_right = new BufferedInputStream(new FileInputStream(right));
//        BufferedReader in_right = new BufferedReader(new InputStreamReader(inputStream_right, "utf-8"), 3*10 * 1024);
        File file_sorted = new File(System.getProperty("user.dir") + "\\temp_files\\file_sorted" + filecounter);
        BufferedWriter out = new BufferedWriter(new FileWriter(file_sorted,true), 32768);
       // BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_sorted, true), "UTF-8"), 3*10 * 1024);
        String line_right;
        List<Dblp_Model> papers_right = new ArrayList<>();
        Gson gson = new Gson();
        while (in_right.ready()) {
            line_right = in_right.readLine();
            Dblp_Model paper = gson.fromJson(line_right, Dblp_Model.class);
            papers_right.add(paper);
        }
        int count = 0;
        String templine;
        while (in_left.ready()) {
            templine=in_left.readLine();
            Dblp_Model paper_left = gson.fromJson(templine, Dblp_Model.class);
            if (paper_left.getTitle().compareTo(papers_right.get(count).getTitle()) <= 0) {
                out.write(templine);
                out.newLine();

            } else {
                while(paper_left.getTitle().compareTo(papers_right.get(count).getTitle()) > 0) {
                    String right_paper=gson.toJson(papers_right.get(count));
                    out.write(right_paper);
                    out.newLine();
                    count++;
                }
                if(paper_left.getTitle().compareTo(papers_right.get(count).getTitle()) <= 0){
                    out.write(templine);
                    out.newLine();
                }
            }
        }
        while(count<papers_right.size()){
            String right_paper=gson.toJson(papers_right.get(count));
            out.write(right_paper);
            out.newLine();
            count++;
        }
        out.close();
        System.out.println(count);
    }
    private static void removefiles(File dir,int index) {
        File[] files=dir.listFiles();
        for(File file:files){
            if(!file.getName().equals("tempfile_fastmode") && !file.getName().equals("file_sorted" + index)){
                System.out.println(file+":"+file.delete());
            }
        }
    }

}


