package com.example.agricultureexpertsapp.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.agricultureexpertsapp.R;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.model.ScrollMode;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.temporal.WeekFields;

import java.util.Calendar;
import java.util.Locale;

import kotlin.Unit;


public class CalendarFragment extends Fragment {

    static TextView monthNameTv;
    Button okBtn, cancelBtn;

    String selectedDateNumber;
    int selectedDay, selectedMonth, selectedYear;
    static YearMonth currentMonth;

    private static Unit invoke(CalendarMonth calendarMonth) {

        currentMonth = calendarMonth.getYearMonth();

        String monthName = calendarMonth.getYearMonth().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        monthNameTv.setText(monthName);

//        System.out.println("Log End Of Month" + currentMonth.atEndOfMonth().getDayOfMonth());
//        System.out.println("Log End Of Month " + currentMonth.atEndOfMonth().getDayOfMonth());

//        int lasDayOfMonth = currentMonth.atEndOfMonth().getDayOfMonth();


        return null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        ImageView imageView = root.findViewById(R.id.backbtn);

        TextView title = root.findViewById(R.id.title);
        monthNameTv = root.findViewById(R.id.monthNameTv);
        title.setText(R.string.title_calendar);
        Button okBtn = root.findViewById(R.id.okBtn);
        CalendarView calendarView = root.findViewById(R.id.calendarView);
        Button cancelBtn = root.findViewById(R.id.cancelBtn);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(DateHandler.GetDate(selectedDateNumber));
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        selectedMonth = calendar.get(Calendar.MONTH) + 1;
        selectedYear = calendar.get(Calendar.YEAR);

        selectedDateNumber = selectedYear + "-" + selectedMonth + "-" + selectedDay;
        calendarView.setDayBinder(new DayViewContainer());

//        currentMonth = YearMonth.now();
        currentMonth = YearMonth.of(selectedYear, selectedMonth);

        calendarView.setScrollMode(ScrollMode.PAGED);
        calendarView.setMonthScrollListener(CalendarFragment::invoke);

        YearMonth lastMonth = currentMonth.plusMonths(11);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        calendarView.setup(currentMonth, lastMonth, firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);

//        calendarView.setScrollMode(ScrollMode.PAGED);
//        calendarView.setMonthScrollListener(FutureDatesActivity::invoke);

        return root;

    }

    class DayViewContainer implements DayBinder<ViewContainer> {

        //        LinearLayout dayLY;
//        TextView dayTxt;
        TextView selectedDay;
        TextView selectedStatus;

        @Override
        public void bind(@NotNull ViewContainer viewContainer, @NotNull CalendarDay calendarDay) {

//            LinearLayout dayLY = viewContainer.getView().findViewById(R.id.dayLY);
            TextView dayTxt = viewContainer.getView().findViewById(R.id.calendarDayText);
            TextView dayStatusTxt = viewContainer.getView().findViewById(R.id.calendarDayStatusIcon);

            LocalDate localDate = calendarDay.getDate();

            dayTxt.setText(String.valueOf(localDate.getDayOfMonth()));

//            String date = DateHandler.FormatDate(localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth());
            String date = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();

//            FutureDatesModel futureDatesModel = futureDatesModelMap != null ? futureDatesModelMap.get(date) : null;

            if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {

                if (date.equals(selectedDateNumber)) {
                    selectDate(dayTxt, dayStatusTxt, localDate);
                }
//                dayStatusTxt.setVisibility(futureDatesModel.hasFreeTimes() ? View.VISIBLE : View.GONE);
                dayStatusTxt.setVisibility(View.GONE);
//                dayStatusTxt.setTextColor(ContextCompat.getColor(getActivity(), futureDatesModel.hasFreeTimes() ? R.color.green : R.color.red));

                dayTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                setOnClick(viewContainer.getView(), dayTxt, dayStatusTxt, localDate);


            } else {
                dayStatusTxt.setVisibility(View.GONE);
                dayTxt.setTextColor(Color.GRAY);
            }


        }

        private void setOnClick(View view, TextView dayTxt, TextView dayStatusTxt, LocalDate localDate) {
            view.setOnClickListener(v -> {
                selectDate(dayTxt, dayStatusTxt, localDate);
            });
        }

        private void selectDate(TextView dayTxt, TextView dayStatusTxt, LocalDate localDate) {

            if (selectedDay != null && selectedDay != dayTxt) {
                selectedDay.setBackground(null);
//                selectedStatus.setVisibility(View.VISIBLE);
//                    selectedDay = dayTxt;
//                    selectedDay.setBackground(ContextCompat.getDrawable(getActiviy(), R.drawable.circle_primary_fill_square_size));
            }
            selectedDay = dayTxt;
            selectedStatus = dayStatusTxt;
//            selectedStatus.setVisibility(View.GONE);
            selectedDay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle_primary_fill));

//                    LocalDate localDate = calendarDay.getDate();
            selectedDateNumber = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();

//            selectedDateTxt.setText(DateHandler.FormatDate4(selectedDateNumber, "yyyy-MM-dd", "EEEE, dd MMMM, yyyy"));


        }

        @NotNull
        @Override
        public ViewContainer create(@NotNull View view) {

//            dayLY = view.findViewById(R.id.dayLY);
//            dayTxt = view.findViewById(R.id.calendarDayText);

//            view.setOnClickListener(v -> {
//                LocalDate localDate = calendarDay.getDate();
//                String date = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
//                Toast(date);
//            });
            return new ViewContainer(view);
        }
    }
}