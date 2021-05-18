package com.example.application.views.agendatelefônica;

import java.util.List;
import java.util.Optional;

import com.example.application.data.DAO.PessoaDao;
import com.example.application.data.entity.Pessoa;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.omg.PortableServer.IdAssignmentPolicyOperations;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;


@Route(value = "agenda/:pessoaID?/:action?(edit)", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Agenda Telefônica")
public class AgendaTelefônicaView extends Div implements BeforeEnterObserver {

    private final String PESSOA_ID = "pessoaID";
    private final String PESSOA_EDIT_ROUTE_TEMPLATE = "agenda/%d/edit";

    private Grid<Pessoa> grid = new Grid<>(Pessoa.class, false);

    private TextField nome;
    private TextField sobrenome;
    private DatePicker data_nascimento;
    private TextField contato;
    private TextField contato_2;
    private TextField contato_3;
    private TextField parentesco;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");


    private BeanValidationBinder<Pessoa> binder;

    private Pessoa pessoa;


    public AgendaTelefônicaView() {
        addClassNames("agenda-telefônica-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nome").setAutoWidth(true);
        grid.addColumn("sobrenome").setAutoWidth(true);
        grid.addColumn("data_nascimento").setAutoWidth(true);
        grid.addColumn("contato").setAutoWidth(true);
        grid.addColumn("contato_2").setAutoWidth(true);
        grid.addColumn("contato_3").setAutoWidth(true);
        grid.addColumn("parentesco").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PESSOA_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));

            } else {
                clearForm();
                UI.getCurrent().navigate(AgendaTelefônicaView.class);
            }
        });


        // Configure Form
        binder = new BeanValidationBinder<>(Pessoa.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            popularGrid();
        });

        delete.addClickListener(e ->{
//            Pessoa p = new Pessoa();
//            p.setNome(nome.getValue());
//            p.setSobrenome(sobrenome.getValue());
//            p.setData_nascimento(data_nascimento.getValue());
//            p.setParentesco(parentesco.getValue());
//            p.setContato(contato.getValue());
//            p.setContato_2(contato_2.getValue());
//            p.setContato_3(contato_3.getValue());

            //binder.readBean(this.pessoa);

            if(pessoa == null){
                Notification.show("Nenhum cadastro selecionado");
                return;
            }
            PessoaDao dao = new PessoaDao();
            if(dao.delete(pessoa)){
                Notification.show("Deletado");
                System.out.println("Deletado");
            }else{
                Notification.show("Não Deletado");
                System.out.println("Não Deletado");
            }
            clearForm();
            popularGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.pessoa == null) {
                    this.pessoa = new Pessoa();
                }

                binder.writeBean(this.pessoa);

                PessoaDao dao = new PessoaDao();
                if(dao.add(pessoa)){
                    Notification.show("Cadastrado");
                    System.out.println("Cadastrado");
                }else{
                    Notification.show("Não Cadastrado");
                    System.out.println("Não Cadastrado");
                }
                clearForm();
                popularGrid();
                Notification.show("Pessoa details stored.");
                UI.getCurrent().navigate(AgendaTelefônicaView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the pessoa details.");
            }
            popularGrid();
        });
        popularGrid();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> pessoaId = event.getRouteParameters().getInteger(PESSOA_ID);
        PessoaDao pessoaDao = new PessoaDao();
        if (pessoaId.isPresent()) {
            Optional<Pessoa> pessoaFromBackend = pessoaDao.getIdPessoa(pessoaId.get());
            if (pessoaFromBackend.isPresent()) {
                populateForm(pessoaFromBackend.get());
            } else {
                Notification.show(String.format("The requested pessoa was not found, ID = %d", pessoaId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                popularGrid();
                event.forwardTo(AgendaTelefônicaView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nome = new TextField("Nome");
        sobrenome = new TextField("Sobrenome");
        data_nascimento = new DatePicker("Data_nascimento");
        contato = new TextField("Contato");
        contato_2 = new TextField("Contato_2");
        contato_3 = new TextField("Contato_3");
        parentesco = new TextField("Parentesco");
        Component[] fields = new Component[]{nome, sobrenome, data_nascimento, contato, contato_2, contato_3,
                parentesco};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel, delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }



    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Pessoa value) {
        this.pessoa = value;
        binder.readBean(this.pessoa);

    }

    private void popularGrid(){
        PessoaDao dao = new PessoaDao();
        List<Pessoa> pessoas = dao.GetlList();
        clearForm();
        grid.setItems(pessoas);

    }
}
