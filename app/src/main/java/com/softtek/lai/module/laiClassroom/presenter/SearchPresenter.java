package com.softtek.lai.module.laiClassroom.presenter;

import com.softtek.lai.common.mvp.BasePersent;
import com.softtek.lai.common.mvp.BaseView1;
import com.softtek.lai.module.laiClassroom.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia.lu on 2017/3/10.
 */

public class SearchPresenter extends BasePersent<SearchPresenter.SearchView> {
    public SearchPresenter(SearchView searchView) {
        super(searchView);
    }

    private List<SearchModel> initDatas(){
        List<SearchModel> modelList = new ArrayList<>();
        SearchModel model1 = new SearchModel(1,0);
        modelList.add(model1);
        SearchModel model2 = new SearchModel(1,1);
        modelList.add(model2);
        SearchModel model3 = new SearchModel(2,0);
        modelList.add(model3);
        SearchModel model4 = new SearchModel(2,1);
        modelList.add(model4);
        SearchModel model5 = new SearchModel(1,1);
        modelList.add(model5);
        SearchModel model6 = new SearchModel(1,1);
        modelList.add(model6);
        SearchModel model7 = new SearchModel(1,0);
        modelList.add(model7);

        return modelList;
    }

    public void getChaosData() {
        if (getView() != null){
            getView().getData(initDatas());
        }
    }

    public interface SearchView extends BaseView1<List<SearchModel>> {

    }
}
