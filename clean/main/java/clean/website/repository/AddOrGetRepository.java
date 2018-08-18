package clean.website.repository;

import java.io.Serializable;

public interface AddOrGetRepository<E, ID extends Serializable, R> {
//    default E addOrGet(E entity, Function<R, E> findExisting) {
//        try {
//            entity = save(entity);
//        } catch (EJBTransactionRolledbackException e) {
//            @SuppressWarnings("unchecked")
//            R repository = (R) this;
//            entity = findExisting.apply(repository);
//        }
//        return entity;
//    }
}
