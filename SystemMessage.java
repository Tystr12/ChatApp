class SystemMessage {

    enum SysMessage {
        CONNECTION_FAILURE("CONNECTION FAILED!", 1),
        CONNECTION_SUCCESS("CONNECTION ESTABLISHED!", 0),
        OTHER_USER_EXIT("Other user exited application", 3);

        private final String message;
        private final int code;

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }

        private SysMessage(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }




}
