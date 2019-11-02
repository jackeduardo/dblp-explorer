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
        System.out.println("1. Rank by id");
        System.out.println("2. Rank by Title");
        System.out.println("3. Rank by cited times");
        System.out.print("Enter the number: ");
        int num = input.nextInt();
        largeFileIO("C:\\Users\\xuhaiyang\\Desktop\\cosc4353\\dblp.v11\\dblp_papers_v11.txt", "output", num);
    }

    public static void largeFileIO(String inputFile, String outputFile, int num) throws IOException {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);
            //List<Dblp_Model> sorted_papers = new ArrayList<>();
            List sorted_papers = new ArrayList<>();
            if (num == 1) {
                sorted_papers = new ArrayList<Integer>();
            } else if (num == 2) {
                sorted_papers = new ArrayList<String>();
            } else {
                sorted_papers = new ArrayList<Integer>();
            }
            FileWriter fw = new FileWriter(outputFile);
            Gson gson = new Gson();
            List<String> papers_title = new ArrayList<>();
            while (in.ready()) {
                String line = in.readLine();
                Dblp_Model paper = gson.fromJson(line, Dblp_Model.class);
                //sorted_papers.add(paper);
                if (num == 1)
                    sorted_papers.add(paper.getId());
                else if (num == 2) {
                    sorted_papers.add(paper.getTitle());
                } else {
                    sorted_papers.add(paper.getReferences().size());
                }
            }
            in.close();
            List sorted_papers_title = (List) sorted_papers.stream().sorted().collect(Collectors.toList());
                sorted_papers_title.forEach(System.out::println);
//            if (num == 1) {
//                result = sorted_papers.stream().sorted(Comparator.comparing(Dblp_Model::getId)).collect(Collectors.toList());
//                result.forEach(paper -> System.out.println("Title is: "+paper.getTitle()));
//            } else if (num == 2) {
//                result = sorted_papers.stream().sorted(Comparator.comparing(Dblp_Model::getTitle)).collect(Collectors.toList());
//                result.forEach(paper -> System.out.println("Title is: "+paper.getTitle()));
//            } else {
//                result = sorted_papers.stream().sorted(Comparator.comparing(Dblp_Model::getreference_length)).collect(Collectors.toList());
//                result.forEach(paper -> System.out.println("Title is: "+paper.getTitle()));
//            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


}