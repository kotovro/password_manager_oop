public class PasswordRepositoryFactory {
    public static PasswordStorage getRepository(Configuration.StorageDescription rd) {
        switch (rd.type) {
            case (Configuration.StorageDescription.FILE): {
                return new FilePasswordStorage(rd);
            }
        }
        return null;
    }
}
