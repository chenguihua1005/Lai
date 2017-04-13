package com.softtek.lai.module.laicheng.presenter;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.mvp.BasePresenter;
import com.softtek.lai.common.mvp.BaseView;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.net.VisitorService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public class HistoryVisitorPresenter extends BasePresenter<HistoryVisitorPresenter.HistoryVisitorView> {

    List<HistoryModel>historyModels=new ArrayList<HistoryModel>();


    public HistoryVisitorPresenter(HistoryVisitorView baseView) {
        super(baseView);
    }

    public void GetData() {
        HistoryModel historyModel1 = new HistoryModel("2017年4月1日 09：00", "张三", "18206182091", "12", "男", "179cm");
        historyModels.add(historyModel1);
        HistoryModel historyModel2 = new HistoryModel("2017年4月2日 09：00", "泽轩", "18206182092", "13", "男", "180cm");
        historyModels.add(historyModel2);
        HistoryModel historyModel3 = new HistoryModel("2017年4月3日 09：00", "怡丽", "18206182093", "14", "女", "168cm");
        historyModels.add(historyModel3);
        HistoryModel historyModel4 = new HistoryModel("2017年4月3日 09：00", "美美", "18206182094", "14", "女", "168cm");
        historyModels.add(historyModel4);
        historyModels.add(historyModel4);
        if (getView()!=null)
        {
            getView().getInfo(historyModels);
        }
    }

    public interface HistoryVisitorView extends BaseView {
         void getInfo(List<HistoryModel> historyModels);
    }
}
