package com.example.carduino.carstatus;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;

import java.beans.PropertyChangeListenerProxy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Carstatus extends Fragment {
    GridLayout gridLayout;
    List<CardModel> cards;

    final int VIEW_COLUMN_COUNT = 4;
    final int VIEW_ROW_COUNT = 3;
    final int MARGIN = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_carstatus, container, false);
        this.gridLayout = r.findViewById(R.id.carstatus_grid_view_container);

        cards = Arrays.stream(CarstatusCardEnum.values()).map(carstatusCardEnum -> {
            CardModel card = new CardModel(carstatusCardEnum.row, carstatusCardEnum.column);
            card.carstatusEnum = carstatusCardEnum.carstatusEnum;
            card.title = carstatusCardEnum.label;

            try {
                card.unit = ((Value) card.carstatusEnum.getType().newInstance()).getUnit();
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                throw new RuntimeException(e);
            }

            return card;
        }).collect(Collectors.toList());

        ViewTreeObserver vto = gridLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final int viewWidth  = gridLayout.getMeasuredWidth();
                final int viewHeight = gridLayout.getMeasuredHeight();

                final int viewColumnSpan = cards.stream().reduce(0, (acc, cur) -> acc + (cur.columnSpan - 1), Integer::sum);
                final int viewRowSpan = cards.stream().reduce(0, (acc, cur) -> acc + (cur.rowSpan - 1), Integer::sum);

                cards.forEach(cardModel -> {
                    cardModel.width = (viewWidth - ((VIEW_COLUMN_COUNT - viewColumnSpan) * (MARGIN * 2))) / VIEW_COLUMN_COUNT;
                    cardModel.height = (viewHeight - ((VIEW_ROW_COUNT - viewRowSpan) * (MARGIN * 2))) / VIEW_ROW_COUNT;

                    CardView cardView = createCardModel(gridLayout, cardModel);

                    cardModel.propertyChangeListener = new PropertyChangeListenerProxy(cardModel.carstatusEnum.name(), new PropertyChangeListener<Value>() {
                        @Override
                        public void onPropertyChange(String propertyName, Value oldValue, Value newValue) {
                            TextView textView = (TextView) cardView.findViewById(R.id.value);
                            textView.setText(newValue.getValue().toString());
                        }
                    });
                    CarStatusSingleton.getInstance().getCarStatus().addPropertyChangeListener(cardModel.propertyChangeListener);
                });
            }
        });

        return r;
    }

    CardView createCardModel(ViewGroup container, CardModel model){
        CardView v = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_text, null);
//        v.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));

        TextView title = v.findViewById(R.id.title);
        title.setText(model.title);
        title.setTextSize(20);
        TextView value = v.findViewById(R.id.value);
        value.setText(model.value);
        TextView unit = v.findViewById(R.id.unit);
        unit.setText(model.unit);
        unit.setTextSize(20);

        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
        lp.setGravity(Gravity.CENTER);
        lp.columnSpec = GridLayout.spec(model.column, model.columnSpan);
        lp.rowSpec = GridLayout.spec(model.row, model.rowSpan);
        lp.height = model.height != null ? model.height * model.rowSpan : GridLayout.LayoutParams.WRAP_CONTENT;
        lp.width = model.width != null ? model.width * model.columnSpan : GridLayout.LayoutParams.WRAP_CONTENT;
        lp.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

        container.addView(v, lp);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cards.forEach(cardModel -> {
            CarStatusSingleton.getInstance().getCarStatus().removePropertyChangeListener(cardModel.propertyChangeListener);
        });
    }
}
