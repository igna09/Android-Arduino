package com.example.carduino.trip;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.models.trip.tripvalue.TripValue;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.TripSingleton;
import com.example.carduino.shared.utilities.LoggerUtilities;

import java.beans.PropertyChangeListenerProxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Trip extends Fragment {
    GridLayout gridLayout;
    List<CardModel> cards;

    final int VIEW_COLUMN_COUNT = 4;
    final int VIEW_ROW_COUNT = 3;
    final int MARGIN = 5;

    private Thread refreshThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_trip, container, false);
        this.gridLayout = r.findViewById(R.id.trip_grid_view_container);

        final com.example.carduino.shared.models.trip.Trip trip = TripSingleton.getInstance().getTrip();
        cards = Arrays.stream(TripCardEnum.values()).map(tripCardEnum -> {
            CardModel card = new CardModel(tripCardEnum.row, tripCardEnum.column);
            card.carstatusEnum = tripCardEnum.carstatusEnum;
            card.title = tripCardEnum.label;
            card.transformator = tripCardEnum.transformator;
            try {
                card.unit = ((Value) card.carstatusEnum.getType().newInstance()).getUnit();
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                throw new RuntimeException(e);
            }
            if(trip.getTripValues().containsKey(tripCardEnum.carstatusEnum.name()) && trip.getTripValues().get(tripCardEnum.carstatusEnum.name()) != null) {
                TripValue v = (TripValue) trip.getTripValues().get(tripCardEnum.carstatusEnum.name());
                if(v.getAverage() != null) {
                    card.setValueWithTransformator(v.getAverage().toString());
                } else {
                    card.value = "-";
                }
            } else {
                card.value = "-";
            }

            return card;
        }).collect(Collectors.toList());

        final int viewColumnSpan = cards.stream().reduce(0, (acc, cur) -> acc + (cur.columnSpan - 1), Integer::sum);
        final int viewRowSpan = cards.stream().reduce(0, (acc, cur) -> acc + (cur.rowSpan - 1), Integer::sum);

        ViewTreeObserver vto = gridLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final int viewWidth  = gridLayout.getMeasuredWidth();
                final int viewHeight = gridLayout.getMeasuredHeight();

                cards.forEach(cardModel -> {
                    cardModel.width = (viewWidth - ((VIEW_COLUMN_COUNT - viewColumnSpan) * (MARGIN * 2))) / VIEW_COLUMN_COUNT;
                    cardModel.height = (viewHeight - ((VIEW_ROW_COUNT - viewRowSpan) * (MARGIN * 2))) / VIEW_ROW_COUNT;

                    cardModel.cardView = addCardView(gridLayout, cardModel);

                    updateCardView(cardModel);
                });
            }
        });

        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(refreshThread.isAlive() && !refreshThread.isInterrupted()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cards.forEach(card -> {
                                TripValue tripValue = TripSingleton.getInstance().getTrip().getTripValues().get(card.carstatusEnum);
                                if(tripValue != null) {
                                    card.setValueWithTransformator(tripValue.getAverage().toString());
                                    updateCardView(card);
                                }
                            });
                        }
                    });
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        LoggerUtilities.logException(e);
                    }
                }
            }
        });
        refreshThread.start();

        return r;
    }

    CardView addCardView(ViewGroup container, CardModel model){
        CardView v = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_text, null);

        TextView title = v.findViewById(R.id.title);
        title.setText(model.title);
        TextView unit = v.findViewById(R.id.unit);
        unit.setText(model.unit);

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

    public void updateCardView(CardModel cardModel) {
        TextView textView = cardModel.cardView.findViewById(R.id.value);
        textView.setText(cardModel.value);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        // TODO: move in onPause?
        cards.forEach(cardModel -> {
            CarStatusSingleton.getInstance().getCarStatus().removePropertyChangeListener(cardModel.propertyChangeListener);
        });

        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshThread.interrupt();
    }
}
