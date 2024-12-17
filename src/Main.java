public class Main {
    public static void main(String[] args) throws Exception {
        BasicEncryptionStandard bes = new BasicEncryptionStandard();
        
        String s = "Andre";
        String bin = bes.stringToBinary(s);
        int[][] key = {
            {0, 1, 0, 1}, 
            {1, 0, 0, 1}, 
            {0, 1, 0, 1}, 
            {0, 0, 1, 1}
        };
        
        System.out.println("'" + s + "' to binary: \n\t" + bin + "\n");
        String encrypted = bes.encrypt(bin, key);
        System.out.println("Encrypted: \n\t" + encrypted);
        String decrypted = bes.decrypt(encrypted, key);
        System.out.println("Decrypted: \n\t" + decrypted);
        
    }
}
