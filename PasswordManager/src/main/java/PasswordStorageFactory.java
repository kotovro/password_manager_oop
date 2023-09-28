public class PasswordStorageFactory {
    public static PasswordStorage getStorage(Configuration.StorageDescription rd) {
        switch (rd.type) {
            case (Configuration.StorageDescription.FILE): {
                return new FilePasswordStorage(rd);
            }
        }
        return null;
    }
}
