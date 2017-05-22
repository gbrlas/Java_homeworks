package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Class which represents a simple notepad with basic text-editing
 * functionalities. Supports Croatian, English and German. Default language is
 * English. Extends JFrame.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List holding all available editors.
	 */
	private ArrayList<JTextArea> editors = new ArrayList<JTextArea>();
	/**
	 * List holding all openedFilePaths.
	 */
	private ArrayList<Path> openedFilePaths = new ArrayList<Path>();
	/**
	 * JTextArea editor.
	 */
	private JTextArea editor;
	/**
	 * Tabbed pane.
	 */
	private JTabbedPane tabs;
	/**
	 * Opened file path.
	 */
	private Path openedFilePath;
	/**
	 * Clip board used for copying/cutting data.
	 */
	private String clipboard;
	/**
	 * Label representing an editor status bar.
	 */
	static JLabel statusBar = new JLabel("length: 0");
	/**
	 * Tab index.
	 */
	static int tabIndex = 0;
	/**
	 * Status bar - length of the selected editor text.
	 */
	int length = 0;
	/**
	 * Status bar - current line of the selected editor text.
	 */
	int line = 1;
	/**
	 * Status bar - current column of the selected editor text.
	 */
	int col = 0;
	/**
	 * Status bar - current size of the selected text.
	 */
	int sel = 0;
	/**
	 * Panel holding the clock and the status bar.
	 */
	static JPanel panel = new JPanel();
	/**
	 * FormLocalizationProvider which connects the frame to the localization
	 * provider.
	 */
	public FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);
	/**
	 * Menu bar.
	 */
	private JMenuBar menuBar = new JMenuBar();
	/**
	 * Green icon representing a non-edited file.
	 */
	private ImageIcon green = new ImageIcon("cd_green.png");
	/**
	 * Green icon representing an edited file.
	 */
	private ImageIcon red = new ImageIcon("cd_red.png");
	/**
	 * String representing current language
	 */
	String language = "en";

	/**
	 * Constructor which creates the frame and sets default size.
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setMinimumSize(new Dimension(375, 375));
		setTitle("JNotepad++");

		initGUI();
	}

	/**
	 * Method which initializes the GUI and adds all needed menus and a toolbar.
	 */
	private void initGUI() {
		tabs = new CloseButtonTabbedPane(this, flp);

		JLabel statusBar = new JLabel();

		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabs.getTabCount() != 0) {
					setTitle(tabs.getToolTipTextAt(tabs.getSelectedIndex())
							+ " - JNotepad++");
				} else {
					setTitle("JNotepad++");
				}
			}
		});

		JPanel tempPanel = new JPanel(new GridLayout(1, 2));
		tempPanel.add(statusBar);
		tempPanel.add(new ClockLabel());

		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		tempPanel.setBorder(border);

		panel.setLayout(new BorderLayout());
		panel.add(tabs, BorderLayout.CENTER);
		panel.add(tempPanel, BorderLayout.PAGE_END);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);

		createActions();
		createMenus();
		createToolbar();

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				programEnd();
			}

		});
	}

	/**
	 * Method which creates the actions and assigns their accelerator key,
	 * mnemonic key and short description.
	 */
	private void createActions() {
		createNewDocument.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		exitAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		toggleCaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control T"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control L"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		getStats.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control alt S"));
		getStats.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		copyAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		pasteAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		cutAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		sortAscendingAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control A"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		sortDescendingAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control D"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		uniqueAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control U"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

	}

	/**
	 * Method which creates all the menus and their sub-menus.
	 */
	private void createMenus() {

		JMenu fileMenu = new JMenu(new LocalizableAction("file", "fileMenu",
				flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createNewDocument));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu(new LocalizableAction("edit", "editMenu",
				flp));
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));

		JMenu toolsMainMenu = new JMenu(new LocalizableAction("tools",
				"toolsMenu", flp));
		menuBar.add(toolsMainMenu);

		JMenu toolsMenu = new JMenu(new LocalizableAction("changeCase",
				"caseMenu", flp));
		JMenu sort = new JMenu(new LocalizableAction("sort", "sortMenu", flp));

		toolsMenu.add(new JMenuItem(toggleCaseAction));
		toolsMenu.add(new JMenuItem(toLowerCaseAction));
		toolsMenu.add(new JMenuItem(toUpperCaseAction));

		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));
		sort.add(new JMenuItem(uniqueAction));

		toolsMainMenu.add(new JMenuItem(getStats));
		toolsMainMenu.add(toolsMenu);
		toolsMainMenu.add(sort);

		JMenu languagesMenu = new JMenu(changeLanguageAction);

		JMenuItem hr = new JMenuItem("Hrvatski");
		hr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
				language = "hr";
			}
		});

		JMenuItem en = new JMenuItem("English");
		en.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				language = "en";
			}
		});

		JMenuItem de = new JMenuItem("Deutsch");
		de.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				language = "de";
			}
		});

		languagesMenu.add(hr);
		languagesMenu.add(en);
		languagesMenu.add(de);

		menuBar.add(languagesMenu);

		setJMenuBar(menuBar);

	}

	/**
	 * Method which creates the toolbar and adds all the needed buttons.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createNewDocument));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(exitAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(getStats));

		getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	/**
	 * Localizable action representing the new document creation.
	 */
	private LocalizableAction createNewDocument = new LocalizableAction("new",
			"blankDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea temp = new JTextArea();

			temp.addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					calculateLabelText(e);
				}

			});

			String ime;

			ime = JOptionPane
					.showInputDialog("Enter temporary new document name: ");

			if (ime == null) {
				return;
			}

			tabs.addTab(ime, red, new JScrollPane(temp));
			tabs.setToolTipTextAt(tabIndex, ime);
			tabs.setSelectedIndex(tabIndex);
			tabIndex++;

			setTitle(tabs.getToolTipTextAt(tabs.getSelectedIndex())
					+ " - JNotepad++");
			editors.add(temp);

			openedFilePaths.add(tabs.getSelectedIndex(), null);
		}
	};

	/**
	 * Localizable action representing opening existing document.
	 */
	private LocalizableAction openDocumentAction = new LocalizableAction(
			"open", "existingDoc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");

			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			openedFilePath = fc.getSelectedFile().toPath();
			if (!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("file") + " " + openedFilePath + " "
								+ flp.getString("notExist"),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean test = false;
			int index = 0;
			for (Path path : openedFilePaths) {
				if (path != null
						&& path.toString().equals(openedFilePath.toString())) {
					JOptionPane.showMessageDialog(JNotepadPP.this,
							flp.getString("alreadyOpen"),
							flp.getString("warning"),
							JOptionPane.WARNING_MESSAGE);
					test = true;
					break;
				}
				index++;
			}

			if (test) {
				tabs.setSelectedIndex(index);
				return;
			}

			byte[] bytes = null;
			try {
				bytes = Files.readAllBytes(openedFilePath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, e1.getMessage(),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}

			JTextArea temp = new JTextArea();

			temp.addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					calculateLabelText(e);
				}
			});

			temp.getDocument().addDocumentListener(new DocumentListener() {

				int i = 0;

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (i != 0) {
						tabs.setIconAt(tabs.getSelectedIndex(), red);
					}
					i++;
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					if (i != 0) {
						tabs.setIconAt(tabs.getSelectedIndex(), red);
					}
					i++;
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					if (i != 0) {
						tabs.setIconAt(tabs.getSelectedIndex(), red);
					}
					i++;
				}
			});

			String ime = openedFilePath.toString().substring(
					openedFilePath.toString().lastIndexOf("\\") + 1);

			temp.setText(new String(bytes, StandardCharsets.UTF_8));

			tabs.addTab(ime, green, new JScrollPane(temp));
			tabs.setToolTipTextAt(tabIndex, openedFilePath.toString());
			tabs.setSelectedIndex(tabIndex);
			tabIndex++;

			setTitle(tabs.getToolTipTextAt(tabs.getSelectedIndex())
					+ " - JNotepad++");
			editors.add(temp);
			openedFilePaths.add(tabs.getSelectedIndex(), openedFilePath);

		}
	};

	/**
	 * Localizable action representing saving the opened document.
	 */
	private LocalizableAction saveDocumentAction = new LocalizableAction(
			"save", "savingDoc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePaths.get(tabs.getSelectedIndex()) == null) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(flp.getString("saveDoc"));

				if (fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this,
							flp.getString("nothingSaved"),
							flp.getString("systemMessage"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				Path file = fc.getSelectedFile().toPath();

				if (Files.exists(file)) {
					int r = JOptionPane.showConfirmDialog(
							JNotepadPP.this,
							flp.getString("file") + " " + file + " "
									+ flp.getString("already"),
							flp.getString("warning"),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (r != JOptionPane.YES_OPTION) {
						return;
					}
				}

				openedFilePath = file;
				String ime = openedFilePath.toString().substring(
						openedFilePath.toString().lastIndexOf("\\") + 1);

				tabs.setToolTipTextAt(tabs.getSelectedIndex(),
						openedFilePath.toString());
				tabs.setTitleAt(tabs.getSelectedIndex(), ime);
				tabs.setIconAt(tabs.getSelectedIndex(), green);

				tabs.getTabComponentAt(tabs.getSelectedIndex()).repaint();

				setTitle(tabs.getToolTipTextAt(tabs.getSelectedIndex())
						+ " - JNotepad++");
			}

			try {
				Files.write(openedFilePath,
						editors.get(tabs.getSelectedIndex()).getText()
								.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, e1.getMessage(),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}

			tabs.setIconAt(tabs.getSelectedIndex(), green);
			revalidate();
			repaint();
		}

	};

	/**
	 * Localizable action representing toggling the case of the selected
	 * document lines.
	 */
	private LocalizableAction toggleCaseAction = new LocalizableAction(
			"toggle", "toggleCase", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			if (duljina == 0) {
				return;
			}

			try {
				String text = doc.getText(pocetak, duljina);
				text = toggleCase(text);

				doc.remove(pocetak, duljina);
				doc.insertString(pocetak, text, null);
			} catch (BadLocationException ignorable) {
			}
		}

		/**
		 * Private method which toggles the case of the passed in string.
		 * 
		 * @param text
		 *            String to be toggled.
		 * @return String with toggled characters.
		 */
		private String toggleCase(String text) {
			char[] znakovi = text.toCharArray();

			for (int i = 0; i < znakovi.length; i++) {
				if (Character.isUpperCase(znakovi[i])) {
					znakovi[i] = Character.toLowerCase(znakovi[i]);
				} else if (Character.isLowerCase(znakovi[i])) {
					znakovi[i] = Character.toUpperCase(znakovi[i]);
				}
			}

			return new String(znakovi);
		}

	};

	/**
	 * Localizable action representing changing the case of the selected
	 * document lines to uppercase.
	 */
	private LocalizableAction toUpperCaseAction = new LocalizableAction(
			"upper", "upperCase", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			if (duljina == 0) {
				return;
			}

			try {
				String text = doc.getText(pocetak, duljina);
				text = toUpper(text);

				doc.remove(pocetak, duljina);
				doc.insertString(pocetak, text, null);
			} catch (BadLocationException ignorable) {
			}
		}

		/**
		 * Private method which changes the String case to uppercase.
		 * 
		 * @param String
		 *            which case is to be changed.
		 * @return Uppercase string.
		 */
		private String toUpper(String text) {
			char[] znakovi = text.toCharArray();

			for (int i = 0; i < znakovi.length; i++) {
				if (Character.isAlphabetic(znakovi[i])) {
					znakovi[i] = Character.toUpperCase(znakovi[i]);
				}
			}

			return new String(znakovi);
		}

	};

	/**
	 * Localizable action representing changing the case of the selected
	 * document lines to lowercase.
	 */
	private LocalizableAction toLowerCaseAction = new LocalizableAction(
			"lower", "lowerCase", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			if (duljina == 0) {
				return;
			}

			try {
				String text = doc.getText(pocetak, duljina);
				text = toLower(text);

				doc.remove(pocetak, duljina);
				doc.insertString(pocetak, text, null);
			} catch (BadLocationException ignorable) {
			}
		}

		/**
		 * Private method which changes the String case to lowercase.
		 * 
		 * @param String
		 *            which case is to be changed.
		 * @return Lowercase string.
		 */
		private String toLower(String text) {
			char[] znakovi = text.toCharArray();

			for (int i = 0; i < znakovi.length; i++) {
				if (Character.isAlphabetic(znakovi[i])) {
					znakovi[i] = Character.toLowerCase(znakovi[i]);
				}
			}

			return new String(znakovi);
		}

	};

	/**
	 * Localizable action representing exiting the program.
	 */
	private LocalizableAction exitAction = new LocalizableAction("exit",
			"exitProgram", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			programEnd();
		}
	};

	/**
	 * Localizable action representing copying the selected part of the
	 * document.
	 */
	private LocalizableAction copyAction = new LocalizableAction("copy",
			"copyDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			try {
				clipboard = doc.getText(pocetak, duljina);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Localizable action representing cutting the selected part of the
	 * document.
	 */
	private LocalizableAction cutAction = new LocalizableAction("cut",
			"cutDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			try {
				clipboard = doc.getText(pocetak, duljina);
				doc.remove(pocetak, duljina);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Localizable action representing pasting the string in the clipboard to
	 * the selected position in the document.
	 */
	private LocalizableAction pasteAction = new LocalizableAction("paste",
			"pasteDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = editor.getCaret().getDot();
			try {
				doc.insertString(pocetak, clipboard, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Localizable action representing the current document stats.
	 */
	private LocalizableAction getStats = new LocalizableAction("stats",
			"statsDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			int chars = 0;
			int nonBlank = 0;
			int lines = 1;

			String text = editors.get(tabs.getSelectedIndex()).getText();

			if (text.isEmpty()) {
				lines = 0;
			}

			for (char znak : text.toCharArray()) {
				chars++;
				if (!Character.isWhitespace(znak)) {
					nonBlank++;
				}
				if (znak == '\n') {
					lines++;
				}
			}

			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("yourDoc") + " " + chars + " "
							+ flp.getString("chars") + " " + nonBlank + " "
							+ flp.getString("nonBlank") + " " + lines + " "
							+ flp.getString("lines"), flp.getString("stats"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Localizable action representing sorting the selected part of the document
	 * in ascending order.
	 */
	private LocalizableAction sortAscendingAction = new LocalizableAction(
			"ascending", "ascDoc", flp) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Locale tempLocale = new Locale(language);
		Collator collator = Collator.getInstance(tempLocale);

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			try {
				int prvaLinija = editor.getLineOfOffset(pocetak);
				int zadnjaLinija = editor.getLineOfOffset(pocetak + duljina);

				ArrayList<String> list = new ArrayList<String>();

				if (prvaLinija == zadnjaLinija) {
					return;
				} else {
					// list containing all lines that need to be sorted
					int indexTemp = 0;
					for (int i = prvaLinija; i <= zadnjaLinija; i++) {
						int temp1 = editor.getLineStartOffset(i);
						int temp2 = editor.getLineEndOffset(i);
						list.add(indexTemp++,
								editor.getText(temp1, temp2 - temp1));
					}

					int index = 0;
					String tempString = "";
					for (int i = 0; i < list.size(); i++) {
						index = i;
						for (int j = i + 1; j < list.size(); j++) {
							if (collator.compare(list.get(j), list.get(i)) < 0) {
								index = j;
							}
						}

						String temp = list.get(i);
						list.add(i, list.get(index));
						list.remove(i + 1);
						list.add(index, temp);
						list.remove(index + 1);

					}

					for (String str : list) {
						tempString += str + "\n";
					}

					tempString = tempString.substring(0,
							tempString.length() - 1);

					doc.remove(pocetak, duljina);
					doc.insertString(pocetak, tempString, null);
				}
			} catch (BadLocationException e1) {
				System.err.println("Error while getting the ascending order.");
			}

		}

	};

	/**
	 * Localizable action representing sorting the selected part of the document
	 * in descending order.
	 */
	private LocalizableAction sortDescendingAction = new LocalizableAction(
			"descending", "descDoc", flp) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Locale tempLocale = new Locale(language);
		Collator collator = Collator.getInstance(tempLocale);

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			try {
				int prvaLinija = editor.getLineOfOffset(pocetak);
				int zadnjaLinija = editor.getLineOfOffset(pocetak + duljina);

				ArrayList<String> list = new ArrayList<String>();

				if (prvaLinija == zadnjaLinija) {
					return;
				} else {
					// list containing all lines that need to be sorted
					int indexTemp = 0;
					for (int i = prvaLinija; i <= zadnjaLinija; i++) {
						int temp1 = editor.getLineStartOffset(i);
						int temp2 = editor.getLineEndOffset(i);
						list.add(indexTemp++,
								editor.getText(temp1, temp2 - temp1));
					}

					int index = 0;
					String tempString = "";
					for (int i = 0; i < list.size(); i++) {
						index = i;
						for (int j = i + 1; j < list.size(); j++) {
							if (collator.compare(list.get(j), list.get(i)) > 0) {
								index = j;
							}
						}

						String temp = list.get(i);
						list.add(i, list.get(index));
						list.remove(i + 1);
						list.add(index, temp);
						list.remove(index + 1);

					}

					for (String str : list) {
						tempString += str + "\n";
					}

					tempString = tempString.substring(0,
							tempString.length() - 1);

					doc.remove(pocetak, duljina);
					doc.insertString(pocetak, tempString, null);
				}
			} catch (BadLocationException e1) {
				System.err.println("Error while getting the ascending order.");
			}

		}

	};

	/**
	 * Localizable action representing removing same lines from the selected
	 * part of the document.
	 */
	private LocalizableAction uniqueAction = new LocalizableAction("unique",
			"uniqueDoc", flp) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = editors.get(tabs.getSelectedIndex());
			Document doc = editor.getDocument();

			int pocetak = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int duljina = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark())
					- pocetak;

			try {
				int prvaLinija = editor.getLineOfOffset(pocetak);
				int zadnjaLinija = editor.getLineOfOffset(pocetak + duljina);

				ArrayList<String> list = new ArrayList<String>();

				if (prvaLinija == zadnjaLinija) {
					return;
				} else {
					// list containing all lines that need to be sorted
					int indexTemp = 0;
					for (int i = prvaLinija; i <= zadnjaLinija; i++) {
						int temp1 = editor.getLineStartOffset(i);
						int temp2 = editor.getLineEndOffset(i);
						list.add(indexTemp++,
								editor.getText(temp1, temp2 - temp1));
					}

					String tempString = "";
					ArrayList<String> tempList = new ArrayList<String>();
					boolean test;
					for (String str : list) {
						test = false;
						for (int i = 0; i < tempList.size(); i++) {
							String tempStr = tempList.get(i).trim();
							test = tempStr.equals(str.trim());
							if (test) {
								break;
							}
						}

						if (!test) {
							tempList.add(str);
						}
					}

					for (String str : tempList) {
						tempString += str;
					}

					doc.remove(pocetak, duljina);
					doc.insertString(pocetak, tempString, null);
				}
			} catch (BadLocationException e1) {
				System.err.println("Error while getting the ascending order.");
			}

		}

	};

	/**
	 * Localizable action representing changing the program language.
	 */
	private LocalizableAction changeLanguageAction = new LocalizableAction(
			"language", "changeDoc", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	};

	/**
	 * Method which asks the user whether he/she really wants to exit the
	 * program.
	 */
	private void programEnd() {
		int r = JOptionPane.showConfirmDialog(JNotepadPP.this,
				flp.getString("krajRada"), flp.getString("warning"),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r != JOptionPane.YES_OPTION) {
			return;
		} else {
			dispose();
		}
	}

	/**
	 * Method which calculates the status bar label text.
	 * 
	 * @param e
	 *            Event representing caret movement.
	 */
	private void calculateLabelText(CaretEvent e) {
		JTextArea text = ((JTextArea) e.getSource());

		int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
				.getMark());
		int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
				.getMark())
				- pocetak;

		length = text.getText().length();

		for (char znak : text.getText().toCharArray()) {
			if (znak == '\n') {
				line++;
				col = 0;
			}
			col++;
		}

		statusBar.setText(new String(flp.getString("length") + " " + length
				+ "\t\t Ln: " + line + " Col: " + col + " Sel: " + duljina));

		if (duljina == 0) {
			menuBar.getMenu(2).getMenuComponent(1).setEnabled(false);
			menuBar.getMenu(2).getMenuComponent(2).setEnabled(false);
		} else {
			menuBar.getMenu(2).getMenuComponent(1).setEnabled(true);
			menuBar.getMenu(2).getMenuComponent(2).setEnabled(true);
		}

		JPanel tempPanel = new JPanel(new GridLayout(1, 2));

		tempPanel.add(statusBar);
		tempPanel.add(new ClockLabel());

		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		tempPanel.setBorder(border);

		panel.remove(1);
		panel.add(tempPanel, BorderLayout.PAGE_END);

		col = 0;
		line = 1;

	}

	/**
	 * Main method which starts the program. Takes no arguments.
	 * 
	 * @param args
	 *            - none.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
			;
		});

	}

}
