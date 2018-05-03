package spp.lab.service;

import spp.lab.models.SafeDeleteEntity;
import spp.lab.models.State;
import spp.lab.reposository.BaseRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


public abstract class BaseService<T extends SafeDeleteEntity, Rep extends BaseRepository<T, Long>> {

    protected Rep repository;


    public boolean checkExistence(Long entity_id) {
        if (repository.findById(entity_id).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    protected Long stringIdToLong(String id)
    {
        return Long.valueOf(id);
    }

    public Optional<T> findByStringId(String id)
    {
        return repository.findOneByIdAndState(this.stringIdToLong(id), State.ACTIVE);
    }

    public void restoreFromTrash(T entity) {
        entity.setState(State.ACTIVE);
        repository.save(entity);
    }

    public void delete(T entity) {
        entity.setState(State.DELETED);
        repository.save(entity);
    }

    public Iterable<T> findAllActive() {
        return repository.findAllByState(State.ACTIVE);
    }

    public Date addDaysToCurrentDate(Long daysToAdd)
    {
        return this.addDaysToGrantedDate(new Date(), daysToAdd);
    }

    public Date addDaysToGrantedDate(Date date, Long daysToAdd)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, Math.toIntExact(daysToAdd));
        return calendar.getTime();
    }

    public void save(T entity)
    {
        this.repository.save(entity);
    }

}
