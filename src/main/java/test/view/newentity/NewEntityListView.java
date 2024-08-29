package test.view.newentity;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.dropdownbutton.DropdownButton;
import io.jmix.flowui.kit.component.dropdownbutton.DropdownButtonItem;
import org.springframework.beans.factory.annotation.Autowired;
import test.entity.NewEntity;
import test.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Route(value = "newEntities", layout = MainView.class)
@ViewController("NewEntity.list")
@ViewDescriptor("new-entity-list-view.xml")
@LookupComponent("newEntitiesDataGrid")
@DialogMode(width = "50em")
public class NewEntityListView extends StandardListView<NewEntity> {

    protected static final String ATTRIBUTE_JMIX_ROLE_VALUE = "jmix-dropdown-button";
    protected static final String ATTRIBUTE_JMIX_ROLE_NAME = "jmix-role";

    @Autowired
    protected DataManager dataManager;
    @Autowired
    private transient UiComponents uiComponents;
    @Autowired
    private Notifications notifications;


    @Install(to = "newEntitiesDl", target = Target.DATA_LOADER)
    protected List<NewEntity> newEntitiesDlLoadDelegate(LoadContext<NewEntity> loadContext) {
        List<NewEntity> list = new ArrayList<>();
        for (int i = 0; i <=100; i++) {
            NewEntity entity = dataManager.create(NewEntity.class);
            list.add(entity);
        }
        return list;
    }



    @Supply(to = "newEntitiesDataGrid.contextMenu", subject = "renderer")
    public Renderer<NewEntity> trafficsDataGridContextMenuRenderer() {
        return new ComponentRenderer<>((NewEntity trafficRequest) -> {
            MenuBar menuBar = new MenuBar();
            menuBar.getElement().setAttribute(ATTRIBUTE_JMIX_ROLE_NAME, ATTRIBUTE_JMIX_ROLE_VALUE);
            MenuItem rootItem = menuBar.addItem("");
            rootItem.add(VaadinIcon.ELLIPSIS_DOTS_V.create(), new Icon("lumo", "dropdown"));
            SubMenu subMenu = rootItem.getSubMenu();
            subMenu.addItem("text", __ -> notifications.create("test")
                    .show());
            return menuBar;
        });
    }
}
