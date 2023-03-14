package vku.vtq.moviesstreamingappclient.Model;

public class Taikhoan {

        String id;
        String name;
        String email;

        String uid;
        String password;


        public Taikhoan(String id, String name, String email, String phone, String uid, String password, String address) {
            this.id = id;
            this.name = name;
            this.email = email;

            this.uid = uid;
            this.password = password;

        }

        public Taikhoan() {
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTen() {
            return name;
        }

        public void setTen(String ten) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }


}
