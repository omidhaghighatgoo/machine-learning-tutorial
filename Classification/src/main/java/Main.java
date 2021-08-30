public class Main {


    public static void main(String[] args) {
        try {
            Classification classification = new Classification();
            classification.toClassify();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
