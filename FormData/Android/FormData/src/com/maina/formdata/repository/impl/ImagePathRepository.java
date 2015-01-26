package com.maina.formdata.repository.impl;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.ImagePath;
import com.maina.formdata.repository.IImagePathRepository;
import com.maina.formdata.repository.RepositoryBase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Patrick on 10/16/2014.
 */
public class ImagePathRepository extends RepositoryBase implements IImagePathRepository {

    public ImagePathRepository(IDataManager dataManager) {
        setClass();
        setDataManager(dataManager);
    }

    @Override
    public List<ImagePath> getByResultId(UUID resultId) throws Exception{
        return getDataManager().publicDao(ImagePath.class).queryForEq("ResultId", resultId);
    }

    @Override
    public List<ImagePath> getAll() throws Exception {
        return getDataManager().getAll(ImagePath.class);
    }

    @Override
    public boolean deleteById(UUID id) throws Exception {
        return getDataManager().publicDao(ImagePath.class).deleteById(id) == 1;
    }

    @Override
    public void setClass() {
        DataClass = ImagePath.class;
    }

    @Override
    public void setDataManager(IDataManager dataManager) {
        setData(dataManager);
    }
}
