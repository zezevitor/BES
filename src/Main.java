public class Main {
    public static void main(String[] args) throws Exception {
        BasicEncryptionStandard bes = new BasicEncryptionStandard();

        String s = "Teste";
        System.out.println("\nAlvo: '" + s + "'");
        
        String bin = bes.stringToBinary(s);
        System.out.println("\n'" + s + "' para binário: \n" + bin);

        int[][] key = bes.generateKey();

        String encrypted = bes.encrypt(bin, key);
        System.out.println("\nEncrypted: " + encrypted);
        String decrypted = bes.decrypt(encrypted, key);
        System.out.println("\nDecrypted: " + decrypted + "\n");
    };
}