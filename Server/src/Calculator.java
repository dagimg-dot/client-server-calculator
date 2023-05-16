public class Calculator {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a,int b) {
        return a - b;
    }

    public static int multiply(int a,int b) {
        return a * b;
    }

    public static double divide(double a,double b) {
        if(b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    public static int modulus(int a,int b) {
        return a % b;
    }

    public static int power(int a,int b) {
        return (int) Math.pow(a,b);
    }

    public static boolean isPrime(int a) {
        if(a <= 1) {
            return false;
        }
        for(int i = 2; i < Math.sqrt(a) + 1; i++) {
            if(a % i == 0) {
                return false;
            }
        }
        return true;
    }
}