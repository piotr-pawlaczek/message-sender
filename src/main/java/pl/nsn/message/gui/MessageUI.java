package pl.nsn.message.gui;

import java.util.List;

import pl.nsn.message.Message;
import pl.nsn.message.MessageBean;
import pl.nsn.message.MessageFactory;
import pl.nsn.message.MessageType;

import com.vaadin.annotations.Title;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


/* 
 * Based on https://github.com/vaadin/addressbook/blob/master/src/main/java/com/vaadin/tutorial/addressbook/AddressbookUI.java
 * 
 * UI class is the starting point for your app. You may deploy it with VaadinServlet
 * or VaadinPortlet by giving your UI class name a parameter. When you browse to your
 * app a web page showing your UI is automatically generated. Or you may choose to 
 * embed your UI to an existing web page. 
 */
@Title("MessageSender")
public class MessageUI extends UI {

	MessageBean messageBean = new MessageBean();

	private Table contactList = new Table();
	private TextField searchField = new TextField();
	private FormLayout editorLayout = new FormLayout();
	private FieldGroup editorFields = new FieldGroup();

	private static final String MESSAGE_TYPE = "Message type";
	private static final String RECIPIENT = "Recipient";
	private static final String MESSAGE = "Message";
	private static final String[] fieldNames = new String[] {
			MESSAGE_TYPE, RECIPIENT, MESSAGE };
	IndexedContainer contactContainer;

	public MessageBean getMessageBean() {
		return this.messageBean;
	}

	protected void init(VaadinRequest request) {
		contactContainer = retrieveData();
		initLayout();
		initContactList();
		initEditor();
		initSearch();
	}

	@SuppressWarnings("unchecked")
	private IndexedContainer retrieveData() {
		IndexedContainer ic = new IndexedContainer();

		for (String p : fieldNames) {
			ic.addContainerProperty(p, String.class, "");
		}

		List<Message> list = this.messageBean.getAllMessages();
		for (int i = 0; i < list.size(); i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, MESSAGE_TYPE).setValue(list.get(i).getType().getName());
			ic.getContainerProperty(id, RECIPIENT).setValue(list.get(i).getRecipient());
			ic.getContainerProperty(id, MESSAGE).setValue(list.get(i).getText());
		}

		return ic;
	}

	private void initLayout() {

		/* Root of the user interface component tree is set */
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		/* Build the component tree */
		VerticalLayout leftLayout = new VerticalLayout();
		splitPanel.addComponent(leftLayout);
		splitPanel.addComponent(editorLayout);
		leftLayout.addComponent(contactList);
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		leftLayout.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(searchField);

		/* Set the contents in the left of the split panel to use all the space */
		leftLayout.setSizeFull();

		/*
		 * On the left side, expand the size of the contactList so that it uses all the space left after from
		 * bottomLeftLayout
		 */
		leftLayout.setExpandRatio(contactList, 1);
		contactList.setSizeFull();
		contactList.setColumnExpandRatio(MESSAGE_TYPE, 1);
		contactList.setColumnExpandRatio(RECIPIENT, 2);
		contactList.setColumnExpandRatio(MESSAGE, 4);

		/*
		 * In the bottomLeftLayout, searchField takes all the width there is after adding addNewContactButton. The
		 * height of the layout is defined by the tallest component.
		 */
		bottomLeftLayout.setWidth("100%");
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

		/* Put a little margin around the fields in the right side editor */
		editorLayout.setMargin(true);
		editorLayout.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void initEditor() {
		final ComboBox messageTypeComboBox = createMessageComboBox();
		editorLayout.addComponent(messageTypeComboBox);

		final TextField recipientField = new TextField("Recipient");
		recipientField.setWidth("50%");
		recipientField.addValidator(new StringLengthValidator(
                "Recipient can not be empty", 1, 100, true));
		recipientField.setImmediate(false);
		editorLayout.addComponent(recipientField);

		final TextArea messageArea = new TextArea("Message");
		messageArea.setWidth("50%");
		editorLayout.addComponent(messageArea);

		Button sendButton = new Button("Send");
		sendButton.addClickListener(new Button.ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				if (recipientField.getValue().equals("")) {
					Notification.show("Please fill recipient field", Type.ERROR_MESSAGE);
					return;
				}
				Message msg = MessageFactory.createMessage(MessageType.fromName((String) messageTypeComboBox.getValue()));
				msg.setText(messageArea.getValue());
				msg.setRecipient(recipientField.getValue());

				getMessageBean().save(msg);

				Notification.show(msg.send(), Type.TRAY_NOTIFICATION);

				contactContainer.removeAllContainerFilters();
				Object contactId = contactContainer.addItemAt(0);

				contactList.getContainerProperty(contactId, MESSAGE_TYPE).setValue(messageTypeComboBox.getValue());
				contactList.getContainerProperty(contactId, RECIPIENT).setValue(recipientField.getValue());
				contactList.getContainerProperty(contactId, MESSAGE).setValue(messageArea.getValue());

			}
		});
		editorLayout.addComponent(sendButton);
		editorFields.setBuffered(false);
	}

	private ComboBox createMessageComboBox() {
		final ComboBox messageTypeComboBox = new ComboBox("Message type");
		messageTypeComboBox.setInvalidAllowed(false);
		messageTypeComboBox.setNullSelectionAllowed(false);
		for (MessageType value : MessageType.values()) {
			messageTypeComboBox.addItem(value.getName());
		}
		messageTypeComboBox.setValue(MessageType.MAIL.getName());
		return messageTypeComboBox;
	}

	private void initSearch() {
		searchField.setInputPrompt("Search contacts");
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);
		searchField.addTextChangeListener(new TextChangeListener() {
			public void textChange(final TextChangeEvent event) {
				contactContainer.removeAllContainerFilters();
				contactContainer.addContainerFilter(new ContactFilter(event.getText()));
			}
		});
	}

	private class ContactFilter implements Filter {
		private String needle;

		public ContactFilter(String needle) {
			this.needle = needle.toLowerCase();
		}

		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(MESSAGE_TYPE).getValue()
					+ item.getItemProperty(RECIPIENT).getValue() + item.getItemProperty(MESSAGE).getValue())
					.toLowerCase();
			return haystack.contains(needle);
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}

	private void initContactList() {
		contactList.setContainerDataSource(contactContainer);
		contactList.setVisibleColumns(new String[] {
				MESSAGE_TYPE, RECIPIENT, MESSAGE });
		contactList.setSelectable(true);
		contactList.setImmediate(true);

	}

}