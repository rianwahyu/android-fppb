package id.co.megadepo.fppb_sidoarjo;

/**
 * Created by addin on 09/11/17.
 */

public class ModelID {
        private String idp;
        private String namep;

        public ModelID() {
        }

        public ModelID(String idp, String namep) {
            this.idp= idp;
            this.namep = namep;
        }

        public String getIdp() {
            return idp;
        }

        public void setIdp(String idp) {
            this.idp = idp;
        }

        public String getNamep() {
            return namep;
        }

        public void setNamep(String namep) {
            this.namep = namep;
        }

        /**
         * Pay attention here, you have to override the toString method as the
         * ArrayAdapter will reads the toString of the given object for the name
         *
         * @return contact_name
         */
        @Override
        public String toString() {
            return idp;
        }
    }
