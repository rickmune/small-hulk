package com.maina.formdata.repository;

import com.maina.formdata.entity.ImagePath;

import java.util.List;
import java.util.UUID;

/**
 * Created by Patrick on 10/16/2014.
 */
public interface IImagePathRepository extends IRepositoryBase {

    public List<ImagePath> getByResultId(UUID resultId) throws Exception;

    public List<ImagePath> getAll() throws Exception;

    public boolean deleteById(UUID id) throws Exception;
}
