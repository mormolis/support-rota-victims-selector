package persistance;

public interface TeamRepository<T> {
    T getTeam();
    void storeTeam(T t);
}
