/*import java.io.*;

public class Server {
    String oldEmail;
    String oldName;
    String oldLastname;
    String oldPassword;
    String oldMerit;

    public Server() {
        super();
    }

    public String readServer() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("database.txt"));

            String var4;
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while(true) {
                    if (line == null) {
                        var4 = sb.toString();
                        break;
                    }

                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
            } catch (Throwable var6) {
                try {
                    br.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            br.close();
            return var4;
        } catch (IOException var7) {
            var7.printStackTrace();
            return "error";
        }
    }

    public void updateEmail(String newEmail) throws IOException {
        String starPrefix = "email:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(starPrefix)) {
                file.seek(file.getFilePointer() - (long)this.readEmail().length());
                file.writeBytes(" " + newEmail + "                    ");
            }
        }

    }

    public String readEmail() throws IOException {
        String startPrefix = "email:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(startPrefix)) {
                this.oldEmail = line.substring(startPrefix.length());
            }
        }

        return this.oldEmail;
    }

    public void updateName(String newName) throws IOException {
        String starPrefix = "name:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(starPrefix)) {
                file.seek(file.getFilePointer() - (long)this.readEmail().length());
                file.writeBytes(" " + newName + "                    ");
            }
        }

    }

    public void updatePassw(String newPassw) throws IOException {
        String starPrefix = "password:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(starPrefix)) {
                file.seek(file.getFilePointer() - (long)this.readPassw().length());
                file.writeBytes(" " + newPassw + "                    ");
            }
        }

    }

    public String readName() throws IOException {
        String startPrefix = "name:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(startPrefix)) {
                this.oldName = line.substring(startPrefix.length());
            }
        }

        return this.oldName;
    }

    public String readLastname() throws IOException {
        String startPrefix = "lastname:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(startPrefix)) {
                this.oldLastname = line.substring(startPrefix.length());
            }
        }

        return this.oldLastname;
    }

    public String readPassw() throws IOException {
        String startPrefix = "password:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(startPrefix)) {
                this.oldPassword = line.substring(startPrefix.length());
            }
        }

        return this.oldPassword;
    }

    public String readMerit() throws IOException {
        String startPrefix = "GPA:";
        RandomAccessFile file = new RandomAccessFile(new File("database.txt"), "rw");

        String line;
        while((line = file.readLine()) != null) {
            if (line.startsWith(startPrefix)) {
                this.oldMerit = line.substring(startPrefix.length());
            }
        }

        return this.oldMerit;
    }
}
*/