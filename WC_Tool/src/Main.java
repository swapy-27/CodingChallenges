import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input = "";

        while(!input.equals("end")){

            input = sc.nextLine().toString();
            System.out.println(input);

        }

    }
}