/*
 * Copyright 2020, Stefan Uebe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.vaadin.stefan.ui;

import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.ui.menu.MenuItem;
import org.vaadin.stefan.ui.view.demos.backgroundevent.DemoCalendarWithBackgroundEvent;
import org.vaadin.stefan.ui.view.demos.basic.BasicDemo;
import org.vaadin.stefan.ui.view.demos.customdaygrid.DemoDayGridWeekWithSixWeeks;
import org.vaadin.stefan.ui.view.demos.customtimeline.DemoTimelineWith28Days;
import org.vaadin.stefan.ui.view.demos.extendedprops.DemoExtendedProps;
import org.vaadin.stefan.ui.view.demos.simple.SimpleDemo;
import org.vaadin.stefan.ui.view.demos.tooltip.DemoWithTooltip;

import static com.github.appreciated.app.layout.entity.Section.HEADER;

import java.util.Locale;

@Push
@PageTitle("FullCalendar Demo")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@CssImport("./app-layout-styles.css")
@SuppressWarnings("rawtypes")
public class MainLayout extends AppLayoutRouterLayout {
	private static final long serialVersionUID = -7479612679602267287L;
	
	public static final String ADDON_VERSION = "3.0.0";

    private void selectCurrentLocale() {
		Locale locale = (Locale) VaadinRequest.getCurrent().getWrappedSession().getAttribute("locale");
        if (locale == null) {
        	locale = UI.getCurrent().getLocale();
        	VaadinRequest.getCurrent().getWrappedSession().setAttribute("locale", locale);
        } else 
        	UI.getCurrent().setLocale(locale);
	}

    @SuppressWarnings("unchecked")
	public MainLayout() {
    	selectCurrentLocale();
        
        Component appBar = AppBarBuilder
                .get()
                .build();

        Component appMenu = generateMenu();

        init(AppLayoutBuilder
                .get(LeftLayouts.LeftHybrid.class)
                .withAppBar(appBar)
                .withAppMenu(appMenu)
                .build());
                
    }
    
    private void addMenu(Object menuBuilder, Class<? extends Component> clazz) {
		MenuItem item = clazz.getAnnotation(MenuItem.class);
		if (menuBuilder instanceof LeftAppMenuBuilder)
			//((LeftAppMenuBuilder)menuBuilder).add(new LeftNavigationItem(item.label(), item.icon().create(), clazz));
			((LeftAppMenuBuilder)menuBuilder).add(new LeftNavigationItem(item.label(), new Icon(), clazz));
		else
			//((LeftSubMenuBuilder)menuBuilder).add(new LeftNavigationItem(item.label(), item.icon().create(), clazz));
			((LeftSubMenuBuilder)menuBuilder).add(new LeftNavigationItem(item.label(), new Icon(), clazz));
	}

	protected Component generateMenu() {
		
		LeftHeaderItem header = new LeftHeaderItem("FullCalendar Demo",
        		"Version " + ADDON_VERSION + " on FC " + FullCalendar.FC_CLIENT_VERSION + " and Vaadin 14.6.3",
                "images/logo.png");
		
		header.getContent().setAlignItems(Alignment.CENTER);
		
		LeftAppMenuBuilder menuBuilder = LeftAppMenuBuilder
                .get()
                .addToSection(HEADER, header);

		addMenu(menuBuilder, BasicDemo.class);
		addMenu(menuBuilder, SimpleDemo.class);
		addMenu(menuBuilder, DemoCalendarWithBackgroundEvent.class);
		addMenu(menuBuilder, DemoWithTooltip.class);
		addMenu(menuBuilder, DemoExtendedProps.class);
		addMenu(menuBuilder, DemoTimelineWith28Days.class);
		addMenu(menuBuilder, DemoDayGridWeekWithSixWeeks.class);

        return menuBuilder.build();
	}
}

