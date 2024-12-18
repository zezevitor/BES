public class Main {
    public static void main(String[] args) throws Exception {
        BasicEncryptionStandard bes = new BasicEncryptionStandard();
        
        String s = "Teste";
        String bin = bes.stringToBinary(s);
        int[][] key = {
            {0, 1, 0, 1}, 
            {1, 0, 0, 1}, 
            {0, 1, 0, 1}, 
            {0, 0, 1, 1}
        };
        
        String encrypted = bes.encrypt(bin, key);
        bes.decrypt(encrypted, key);
        
    }
}
