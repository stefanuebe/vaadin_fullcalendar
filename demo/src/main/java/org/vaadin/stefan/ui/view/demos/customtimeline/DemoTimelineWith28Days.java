package org.vaadin.stefan.ui.view.demos.customtimeline;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.stefan.fullcalendar.CalendarLocale;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import org.vaadin.stefan.fullcalendar.model.Header;
import org.vaadin.stefan.fullcalendar.model.HeaderFooterItem;
import org.vaadin.stefan.fullcalendar.model.HeaderFooterPart;
import org.vaadin.stefan.ui.MainLayout;
import org.vaadin.stefan.ui.menu.MenuItem;

@Route(value = "demotimelinecustomdays", layout = MainLayout.class)
@PageTitle("FC with 28 days timeline")
@MenuItem(label = "28 Days Timeline")
public class DemoTimelineWith28Days extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    
    private FullCalendar calendar;

    public DemoTimelineWith28Days() {
    	initView();
    	
    	createCalendarInstance();
    	
    	addAndExpand(calendar);
    }
    
    private void initView() {
    	setSizeFull();
    }

    private void createCalendarInstance() {
        CustomDaysTimelineCalendarView calendarView = new CustomDaysTimelineCalendarView(28);
        
        calendar = FullCalendarBuilder.create().withScheduler("GPL-My-Project-Is-Open-Source").withInitialOptions(calendarView.getInitialOptions()).build();
        calendar.setSizeFull();
        calendar.setLocale(CalendarLocale.getDefault());
        
        Header testHeader = new Header();
        
        HeaderFooterPart headerLeft = testHeader.getStart();
        headerLeft.addItem(HeaderFooterItem.TITLE);
        
        HeaderFooterPart headerRight = testHeader.getEnd();
        headerRight.addItem(HeaderFooterItem.BUTTON_PREVIOUS);
        headerRight.addItem(HeaderFooterItem.BUTTON_TODAY);
        headerRight.addItem(HeaderFooterItem.BUTTON_NEXT);
        
        calendar.setHeaderToolbar(testHeader);

        calendar.changeView(calendarView);
    }
}