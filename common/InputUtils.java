package case_study.common;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Yêu cầu người dùng nhập thông tin và xác thực dữ liệu đó dựa trên điều kiện.
     *
     * @param prompt     Thông báo yêu cầu người dùng nhập thông tin.
     * @param validator  Hàm xác thực thông tin nhập vào.
     * @return           Dữ liệu hợp lệ từ người dùng.
     */
    public static String getValidatedInput(String prompt, Predicate<String> validator) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!validator.test(input)) {
                System.out.println("Invalid data or data already exists. Please re-enter.");
            }
        } while (!validator.test(input));
        return input;
    }
}
