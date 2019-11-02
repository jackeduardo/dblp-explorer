import java.util.List;
import java.util.Map;

public class Dblp_Model {
    private String id;
    private String title;
    private List<Authors> authors;
    private Venue venue;
    private int year;
    private List<String> keywords;
    private List<Fos> fos;
    private List<String> references;
    private int n_citation;
    private String page_start;
    private String page_end;
    private String doc_type;
    private String lang;
    private String publisher;
    private String volume;
    private String issue;
    private String issn;
    private String isbn;
    private String doi;
    private String pdf;
    private List<String> url;
    //private Map<String,String> indexed_abstract;

//    public Map<String, String> getIndexed_abstract() {
//        return indexed_abstract;
//    }
//
//    public void setIndexed_abstract(Map<String, String> indexed_abstract) {
//        this.indexed_abstract = indexed_abstract;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getreference_length() {
        if (getReferences() != null)
            return getReferences().size();
        else
            return 0;
    }

    public void setAuthors(List<Authors> authors) {
        this.authors = authors;
    }

    public List<Authors> getAuthors() {
        return authors;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setFos(List<Fos> fos) {
        this.fos = fos;
    }

    public List<Fos> getFos() {
        return fos;
    }

    public int getN_citation() {
        return n_citation;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public String getDoi() {
        return doi;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIssn() {
        return issn;
    }

    public String getIssue() {
        return issue;
    }

    public String getLang() {
        return lang;
    }

    public String getPage_end() {
        return page_end;
    }

    public String getPage_start() {
        return page_start;
    }

    public String getPdf() {
        return pdf;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getVolume() {
        return volume;
    }

    public List<String> getReferences() {
        return references;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setN_citation(int n_citation) {
        this.n_citation = n_citation;
    }

    public void setPage_end(String page_end) {
        this.page_end = page_end;
    }

    public void setPage_start(String page_start) {
        this.page_start = page_start;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


}

