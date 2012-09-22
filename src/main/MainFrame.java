package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import mobs.Mob;
import mobs.SwarmMob;
import projectiles.Projectile;
import towers.BreathSpray;
import towers.ElecTB;
import towers.MouthWash;
import towers.TongueCleaner;
import towers.ToothBrush;
import towers.ToothPaste;
import towers.Tower;
import basics.Base;
import basics.Movable;
import basics.Tile;

/**
 * This is the Main Frame for the game, Candy TD.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// Massive stack of variables.
	public static final int MAX_WIDTH = 832;
	public static final int MAX_HEIGHT = 512;
	public static final int GRID_WIDTH = 26;
	public static final int GRID_HEIGHT = 16;
	public static final int TILE_SIZE = 32;
	private static final int TICKS_PER_WAVE = 400;
	private static final int TICKS_BETWEEN_MOBS = 8;
	private static final int NUMBER_MOBS_PER_WAVE = 25;
	private static final int LAST_WAVE = 50;
	private static final Image[][] MOB_IMAGES = {
			{new ImageIcon("Images\\Monsters\\SkittleGR.png").getImage()}, // 0
			{new ImageIcon("Images\\Monsters\\SkittleYO.png").getImage()}, // 1
			{new ImageIcon("Images\\Monsters\\JellyR1.png").getImage(),
					new ImageIcon("Images\\Monsters\\JellyR2.png").getImage()}, // 2
			{new ImageIcon("Images\\Monsters\\JellyG1.png").getImage(),
					new ImageIcon("Images\\Monsters\\JellyG2.png").getImage()}, // 3
			{new ImageIcon("Images\\Monsters\\JellyB1.png").getImage(),
					new ImageIcon("Images\\Monsters\\JellyB2.png").getImage()}, // 4
			{new ImageIcon("Images\\Monsters\\Lolli1.png").getImage(),
					new ImageIcon("Images\\Monsters\\Lolli2.png").getImage()}, // 5
			{new ImageIcon("Images\\Monsters\\Candy.png").getImage()}, // 6
			{new ImageIcon("Images\\Monsters\\Choc1.png").getImage(),
					new ImageIcon("Images\\Monsters\\Choc2.png").getImage(),
					new ImageIcon("Images\\Monsters\\Choc3.png").getImage(),
					new ImageIcon("Images\\Monsters\\Choc4.png").getImage()}, // 7
			{new ImageIcon("Images\\Monsters\\Cupcake.png").getImage()}, // 8
			{new ImageIcon("Images\\Monsters\\Plaque1.png").getImage(),
					new ImageIcon("Images\\Monsters\\Plaque2.png").getImage(),
					new ImageIcon("Images\\Monsters\\Plaque3.png").getImage(),
					new ImageIcon("Images\\Monsters\\Plaque4.png").getImage()} // 9
	};
	private static final Image[][] TOWER_IMAGES = {
			{
					new ImageIcon("Images\\Towers\\Toothbrush1.png").getImage(),
					new ImageIcon("Images\\Towers\\Toothbrush2.png").getImage(),
					new ImageIcon("Images\\Towers\\Toothbrush3.png").getImage()},// 0
			{
					new ImageIcon("Images\\Towers\\Toothpaste1.png").getImage(),
					new ImageIcon("Images\\Towers\\Toothpaste2.png").getImage(),
					new ImageIcon("Images\\Towers\\Toothpaste3.png").getImage()},// 1
			{
					new ImageIcon("Images\\Towers\\BreathSpray1.png")
							.getImage(),
					new ImageIcon("Images\\Towers\\BreathSpray2.png")
							.getImage(),
					new ImageIcon("Images\\Towers\\BreathSpray3.png")// 2
							.getImage()},
			{new ImageIcon("Images\\Towers\\ElecTB1.png").getImage(),
					new ImageIcon("Images\\Towers\\ElecTB2.png").getImage(),
					new ImageIcon("Images\\Towers\\ElecTB3.png").getImage()},// 3
			{new ImageIcon("Images\\Towers\\MW1.png").getImage(),
					new ImageIcon("Images\\Towers\\MW2.png").getImage(),
					new ImageIcon("Images\\Towers\\MW2.png").getImage()}, // 4
			{new ImageIcon("Images\\Towers\\TC1.png").getImage(),
					new ImageIcon("Images\\Towers\\TC2.png").getImage(),
					new ImageIcon("Images\\Towers\\TC3.png").getImage()}// 5
	};
	private static final Image[][] TOWER_PANEL_IMAGES = {
			{new ImageIcon("Images\\Towers\\ToothbrushPanel.png").getImage()},
			{new ImageIcon("Images\\Towers\\ToothpastePanel.png").getImage()},
			{new ImageIcon("Images\\Towers\\BreathSprayPanel.png").getImage()},
			{new ImageIcon("Images\\Towers\\ElecTBPanel.png").getImage()},
			{new ImageIcon("Images\\Towers\\MWPanel.png").getImage()},
			{new ImageIcon("Images\\Towers\\TCPanel.png").getImage()}};
	public static final Image[] PROJECTILE_IMAGES = {
			new ImageIcon("Images\\Projectiles\\Bristle.png").getImage(),
			new ImageIcon("Images\\Projectiles\\TP1.png").getImage(),
			new ImageIcon("Images\\Projectiles\\TP2.png").getImage(),
			new ImageIcon("Images\\Projectiles\\TP3.png").getImage(),
			new ImageIcon("Images\\Projectiles\\BSplash1.png").getImage(),
			new ImageIcon("Images\\Projectiles\\BSplash2.png").getImage(),
			new ImageIcon("Images\\Projectiles\\BSplash3.png").getImage(),
			new ImageIcon("Images\\Projectiles\\MWSplash1.png").getImage(),
			new ImageIcon("Images\\Projectiles\\MWSplash2.png").getImage(),
			new ImageIcon("Images\\Projectiles\\MWSplash3.png").getImage()};
	private static final String[] TOWER_DESC = {
			"This tower will fire bristles at enemies. \nWhen upgraded,"
					+ "it can fire piercing projectiles, hitting more than "
					+ "one enemy. \nCost: 30 \nDamage: 1 \nFire Rate: 1 shot/3 game ticks.",
			"This tower will fire toothpaste blobs at enemies. \nThis will"
					+ " slow down the enemy that it hits by 50%. \nCost: 50 "
					+ "\nDamage: 1 \nFire Rate: 1 shot/4 game ticks.",
			"This tower will fire a spray that can deal splash damage. "
					+ "\ni.e. it can deal damage to surrounding mobs. "
					+ "\nCost: 80 \nDamage: 3 \nFire Rate: 1 shot/5 game ticks.",
			"This tower will fire a large amount of bristles. "
					+ "\nThis tower is a lot faster than the regular toothbrush."
					+ "\nCost: 130 \nDamage: 2 \nFire Rate: 1 shot/1 game tick.",
			"This tower will fire a large blob of mouthwash. "
					+ "\nThis is a very powerful tower. "
					+ "\nCost: 100 \nDamage: 6 \nFire Rate: 1 shot/6 game ticks.",
			"This tower will spin around and around and around forever. "
					+ "\nThis will damage all mobs that it touches. "
					+ "\nCost: 90 \nDamage: 5 \n "};
	private static final String[] CUTSCENES = { // TODO Make this a changeable file perhaps?
			"“You have put up a good fight tooth fairy, but here’s where the fun ends.\n"
					+ "I will grant you the privilege of being the first to fall to my newest creation,” cackled Tom Quaple.\n"
					+ "Tom pulled out a small container from his pocket, \nand dumped what appeared to be two small piles of brown sludge on the ground.\n"
					+ "He then proceeded to take out a small vial of red liquid, and poured one drop on each of the brown piles.\n"
					+ "The brown piles of sludge began to quickly grow, and took on the form of a ghastly four legged blob.\n"
					+ "The two blobs began to slowly crawl towards the tooth fairy, and she knew that she had to prevent them from reaching her.",
			"As the two mysterious monsters began to fall, the tooth fairy heard a faint gasp.\n"
					+ "She looked into the eyes of the creatures, and realized that they were trying to communicate to her.\n"
					+ "“Th…thank you for f…freeing us.  P-Please stop our son… he…plaque,” they moaned.\n"
					+ "The tooth fairy then realized the shocking truth.\n"
					+ "Tom Quaple had turned his own parents into plaque monsters, and they were trying to warn her about something.\n"
					+ "Just as she was about to reply, the two plaque monsters shrank back down and started glowing.\n"
					+ "After a few seconds of glowing, the intensity of the light began to increase.\n"
					+ "The light became brighter and stronger until it became unbearable for the tooth fairy."
					+ "The tooth fairy quickly hid behind her tooth fortress, as she heard the distance laugh of Tom Quaple.\n"
					+ "A few seconds later, the light began to weaken.\n"
					+ "The tooth fairy looked out, and realized that while she had been hiding herself from the light,\n"
					+ " Tom had begun assembling even stronger sweets, and was sending them forward for attack.",
			"“Curse you tooth fairy!  I am running out of sweets.  I guess I must go to my last resort…”\n"
					+ "said Tom Quaple.Tom took out the red vial again, and this time he gulped down the entire vial.\n"
					+ "A few seconds passed, and Tom began to spasm.  His body started twitching, and growing in size.\n"
					+ "Tom was morphing into one of the plaque monsters that the tooth fairy had witnessed earlier,\n      but this time she sensed a greater darkness.\n"
					+ "After the transformation was complete, Tom had changed into an evil plaque monster with a sinister face.\n"
					+ "Knowing that this would be the last standoff, the tooth fairy regained her will to fight and readied herself for the final confrontation."};
	private static final Mob[] MOBS = {
			new Mob(2, 4, 180, 16, 16, 1, MOB_IMAGES[0]),// 1
			new Mob(4, 4, 180, 16, 16, 1, MOB_IMAGES[2]),// 2
			new Mob(4, 8, 180, 16, 16, 2, MOB_IMAGES[6]),// 3
			new Mob(9, 4, 180, 16, 16, 2, MOB_IMAGES[3]),// 4
			new SwarmMob(20, 4, 180, 16, 16, 10, MOB_IMAGES[7], true, 0),// 5
			new Mob(12, 4, 180, 16, 16, 2, MOB_IMAGES[4]),// 6
			new Mob(15, 4, 180, 16, 16, 3, MOB_IMAGES[1]),// 7
			new Mob(18, 4, 180, 16, 16, 3, MOB_IMAGES[5]),// 8
			new Mob(21, 4, 180, 16, 16, 3, MOB_IMAGES[3]),// 9
			new Mob(50, 2, 180, 16, 16, 12, MOB_IMAGES[8]),// 10
			new Mob(25, 4, 180, 16, 16, 3, MOB_IMAGES[2]),// 11
			new Mob(30, 4, 180, 16, 16, 4, MOB_IMAGES[0]),// 12
			new Mob(35, 4, 180, 16, 16, 4, MOB_IMAGES[2]),// 13
			new Mob(30, 8, 180, 16, 16, 4, MOB_IMAGES[6]),// 14
			new SwarmMob(100, 4, 180, 16, 16, 20, MOB_IMAGES[7], true, 0),// 15
			new Mob(45, 4, 180, 16, 16, 5, MOB_IMAGES[5]),// 16
			new Mob(50, 4, 180, 16, 16, 5, MOB_IMAGES[4]),// 17
			new Mob(55, 4, 180, 16, 16, 6, MOB_IMAGES[1]),// 18
			new Mob(60, 4, 180, 16, 16, 6, MOB_IMAGES[3]),// 19
			new Mob(150, 4, 180, 16, 16, 100, MOB_IMAGES[8]),// 20
			new Mob(70, 4, 180, 16, 16, 6, MOB_IMAGES[0]),// 21
			new Mob(80, 4, 180, 16, 16, 7, MOB_IMAGES[2]),// 22
			new Mob(70, 8, 180, 16, 16, 7, MOB_IMAGES[6]),// 23
			new Mob(100, 4, 180, 16, 16, 7, MOB_IMAGES[3]),// 24
			new Mob(500, 4, 180, 16, 16, 200, MOB_IMAGES[9]),// 25
			new Mob(110, 4, 180, 16, 16, 7, MOB_IMAGES[4]),// 26
			new Mob(120, 4, 180, 16, 16, 8, MOB_IMAGES[1]),// 27
			new Mob(130, 4, 180, 16, 16, 8, MOB_IMAGES[0]),// 28
			new Mob(140, 4, 180, 16, 16, 8, MOB_IMAGES[3]),// 29
			new Mob(750, 2, 180, 16, 16, 300, MOB_IMAGES[8]),// 30
			new Mob(150, 4, 180, 16, 16, 8, MOB_IMAGES[2]),// 31
			new Mob(160, 4, 180, 16, 16, 8, MOB_IMAGES[0]),// 32
			new Mob(170, 4, 180, 16, 16, 8, MOB_IMAGES[2]),// 33
			new Mob(150, 8, 180, 16, 16, 9, MOB_IMAGES[6]),// 34
			new SwarmMob(900, 4, 180, 16, 16, 400, MOB_IMAGES[7], true, 0),// 35
			new Mob(190, 4, 180, 16, 16, 9, MOB_IMAGES[5]),// 36
			new Mob(200, 4, 180, 16, 16, 9, MOB_IMAGES[4]),// 37
			new Mob(215, 4, 180, 16, 16, 9, MOB_IMAGES[1]),// 38
			new Mob(230, 4, 180, 16, 16, 9, MOB_IMAGES[3]),// 39
			new Mob(1250, 4, 180, 16, 16, 500, MOB_IMAGES[8]),// 40
			new Mob(245, 4, 180, 16, 16, 10, MOB_IMAGES[0]),// 41
			new Mob(260, 4, 180, 16, 16, 10, MOB_IMAGES[2]),// 42
			new Mob(225, 8, 180, 16, 16, 10, MOB_IMAGES[6]),// 43
			new Mob(290, 4, 180, 16, 16, 10, MOB_IMAGES[3]),// 44
			new SwarmMob(1666, 4, 180, 16, 16, 750, MOB_IMAGES[7], true, 0),// 45
			new Mob(325, 4, 180, 16, 16, 15, MOB_IMAGES[4]),// 46
			new Mob(350, 4, 180, 16, 16, 15, MOB_IMAGES[1]),// 47
			new Mob(375, 4, 180, 16, 16, 15, MOB_IMAGES[5]),// 48
			new Mob(400, 4, 180, 16, 16, 20, MOB_IMAGES[3]),// 49
			new Mob(2000, 2, 180, 16, 16, 1000, MOB_IMAGES[9])};// 50
	private static final Tower[] TOWERS = {
			new ToothBrush(30, 1, 80, 0, 0, 0, 3, TOWER_IMAGES[0]),
			new ToothPaste(50, 1, 120, 0, 0, 0, 4, 0.5, 15, TOWER_IMAGES[1]),
			new BreathSpray(80, 3, 95, 0, 0, 0, 5, 45, TOWER_IMAGES[2]),
			new ElecTB(130, 2, 80, 0, 0, 0, 1, TOWER_IMAGES[3]),
			new MouthWash(100, 6, 180, 0, 0, 0, 6, TOWER_IMAGES[4]),
			new TongueCleaner(90, 5, 0, 0, 0, 0, TOWER_IMAGES[5])};
	private static final Tower[] TOWERS_PANEL = {
			new ToothBrush(30, 1, 80, 0, 0, 0, 3, TOWER_PANEL_IMAGES[0]),
			new ToothPaste(50, 1, 120, 0, 0, 0, 4, 0.5, 15,
					TOWER_PANEL_IMAGES[1]),
			new BreathSpray(80, 3, 95, 0, 0, 0, 5, 45, TOWER_PANEL_IMAGES[2]),
			new ElecTB(130, 2, 80, 0, 0, 0, 1, TOWER_PANEL_IMAGES[3]),
			new MouthWash(100, 6, 180, 0, 0, 0, 6, TOWER_PANEL_IMAGES[4]),
			new TongueCleaner(90, 5, 0, 0, 0, 0, TOWER_PANEL_IMAGES[5])};

	// The board and all the objects in the board.
	private Tile[] board;
	private ArrayList<Movable> movingObjs;
	// The movement costs for each tile in the board. This has to be atomic, so
	// as to help prevent crashing when it is being accessed by both the timer
	// thread and the main thread (the responsive one)
	private volatile int[][] boardCosts;
	// The modes that you are in.
	private boolean inMenu;
	private boolean inTowerPlacement;
	private boolean inHelp;
	private boolean gameStarted;
	private boolean inCutscene;
	private int cutscene;
	// Basic stats.
	private int money;
	private int health;
	private int currWave;
	private final int NORMAL_MONEY = 100;
	private final int NORMAL_HEALTH = 20;
	// For the timer.
	private int currMobTime;
	private int currWaveTime;
	private int mobsReleased;

	private int towerFromPanel;
	private Base selectedFromMain;

	private Timer timer;
	// The JPanels.
	private MainPanel mainScreen;
	private TowerPanel towerScreen;
	private DescPanel descScreen;
	// Images.
	private Image redImg = new ImageIcon("Images\\Tiles\\red.png").getImage();
	private Image greenImg = new ImageIcon("Images\\Tiles\\green.png")
			.getImage();
	private Image bg = new ImageIcon("Images\\Grid.png").getImage();
	private Image towerBack = new ImageIcon("Images\\Towers\\back.png")
			.getImage();
	private Image menu = new ImageIcon("Images\\Menu.png").getImage();
	private Image help = new ImageIcon("Images\\Instructions.png").getImage();

	/**
	 * Make a Frame with the given title.
	 * 
	 * @param str
	 *            the title of the Frame.
	 * @throws IOException
	 *             when the images are not found.
	 */
	public MainFrame(String str) throws IOException{
		super(str);
		inMenu = true;
		inTowerPlacement = false;
		gameStarted = false;

		// Create the MenuBar, and include stuff in it.
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu, helpMenu;
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		JMenuItem newItem, quitItem, menuItem, aboutItem, howToPlayItem;
		newItem = new JMenuItem("New Game", KeyEvent.VK_N);
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		newItem.addActionListener(new ActionListener(){
			/**
			 * This will start a new game.
			 * 
			 * @param event
			 *            the <code>ActionEvent</code>.
			 */
			@Override
			public void actionPerformed(ActionEvent event){
				try{
					newGame();
				} catch(FileNotFoundException e){
				}
			}
		});

		quitItem = new JMenuItem("Quit Game", KeyEvent.VK_Q);
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		quitItem.addActionListener(new ActionListener(){
			/**
			 * This will quit the game.
			 * 
			 * @param event
			 *            the <code>ActionEvent</code>.
			 */
			@Override
			public void actionPerformed(ActionEvent event){
				quitGame();
			}
		});

		menuItem = new JMenuItem("Go to Menu", KeyEvent.VK_M);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener(){
			/**
			 * This will go to the menu.
			 * 
			 * @param event
			 *            the <code>ActionEvent</code>.
			 */
			@Override
			public void actionPerformed(ActionEvent event){
				goToMenu();
			}
		});

		fileMenu.add(menuItem);
		fileMenu.addSeparator();
		fileMenu.add(newItem);
		fileMenu.add(quitItem);
		bar.add(fileMenu);

		aboutItem = new JMenuItem("About Candy TD");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
				KeyEvent.CTRL_MASK));
		aboutItem.addActionListener(new ActionListener(){
			/**
			 * This will display a popup about who made this game.
			 * 
			 * @param event
			 *            the <code>ActionEvent</code>.
			 */
			@Override
			public void actionPerformed(ActionEvent event){
				aboutPopup();
			}
		});

		howToPlayItem = new JMenuItem("How to Play");
		howToPlayItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		howToPlayItem.addActionListener(new ActionListener(){
			/**
			 * This will display the help menu.
			 * 
			 * @param event
			 *            the <code>ActionEvent</code>.
			 */
			@Override
			public void actionPerformed(ActionEvent event){
				displayHelpMenu();
			}
		});

		helpMenu.add(aboutItem);
		helpMenu.add(howToPlayItem);
		bar.add(helpMenu);
		this.setJMenuBar(bar);

		mainScreen = new MainPanel();
		towerScreen = new TowerPanel();
		descScreen = new DescPanel();

		this.getContentPane().add(mainScreen, BorderLayout.CENTER);
		this.getContentPane().add(towerScreen, BorderLayout.NORTH);
		this.getContentPane().add(descScreen, BorderLayout.SOUTH);
		this.pack();
		mainScreen.setVisible(true);
		towerScreen.setVisible(true);
		descScreen.setVisible(true);
		this.setVisible(true);
		this.addKeyListener(new KeyListener(){
			/**
			 * This handles any pressed keys for this frame.
			 * 
			 * @param e
			 *            the KeyEvent.
			 */
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					exitTowerPlacement();
				else if(e.getKeyCode() == KeyEvent.VK_N && inCutscene){
					// End the cutscene.
					if(inCutscene){
						inCutscene = false;
						cutscene++;
						lastTime = MINTIME;
						timer.start();
					}
				} else if(e.getKeyCode() == KeyEvent.VK_C
						&& e.getModifiers() == KeyEvent.CTRL_MASK){
					System.out.println("asdf");
					// Cheat mode!
					displayCheatBox();
				} else if(e.getKeyCode() >= KeyEvent.VK_1
						&& e.getKeyCode() <= KeyEvent.VK_6){
					// Keyboard shortcuts for towers.
					inTowerPlacement = true;
					towerFromPanel = e.getKeyCode() - KeyEvent.VK_1;
				} else if(e.getKeyCode() == KeyEvent.VK_U
						&& selectedFromMain != null){
					// Do an upgrade!
					doUpgrade();
				} else if(e.getKeyCode() == KeyEvent.VK_S
						&& selectedFromMain != null){
					// Sell the tower!
					sellTower();
				}
			}

			@Override
			public void keyReleased(KeyEvent e){
			}

			@Override
			public void keyTyped(KeyEvent e){
			}
		});
	}

	/**
	 * Input some cheats!
	 */
	private void displayCheatBox(){
		// The cheat code is the course code.
		String temp = JOptionPane.showInputDialog(this,
				"Enter the cheat code.", "Cheater!", JOptionPane.PLAIN_MESSAGE);
		if(temp != null && temp.equalsIgnoreCase("ics4u")){
			health = 9001;
			money = 9001;
		}
	}

	/**
	 * This displays a popup about who made this game.
	 */
	protected void aboutPopup(){
		JOptionPane.showMessageDialog(this,
				"This program, Candy TD, was made by Collier"
						+ " Jiang, Kevin Lau, and Kevin Choi.",
				"About Candy TD", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * This starts a new game.
	 */
	protected void newGame() throws FileNotFoundException{
		// Initialise all the variables.
		currWave = 0;
		inMenu = false;
		inHelp = false;
		inTowerPlacement = false;
		gameStarted = false;
		inCutscene = false;
		cutscene = 0;
		health = NORMAL_HEALTH;
		money = NORMAL_MONEY;
		movingObjs = new ArrayList<Movable>();

		// Create the board and input the grid data from the grid file.
		board = new Tile[GRID_HEIGHT * GRID_WIDTH];
		Scanner in = new Scanner(new File("grid.txt"));
		int i = 0;
		while(in.hasNextLine()){
			String next = in.nextLine();
			for(int j = 0; j < next.length(); j++){
				board[i] = new Tile(
						(i % GRID_WIDTH) * (MAX_WIDTH / GRID_WIDTH),
						(i / GRID_WIDTH) * (MAX_HEIGHT / GRID_HEIGHT),
						"0123ACD".indexOf(next.charAt(j)) >= 0, new ImageIcon(
								"Images\\Tiles\\" + next.charAt(j) + ".png")
								.getImage());
				i++;
			}
		}
		in.close();
		// Stop the timer, if it's already running.
		if(timer != null){
			timer.stop();
		}
		// Calculate the board costs (for movement).
		calculateBoardCosts(GRID_WIDTH - 2, GRID_HEIGHT - 2, false);
		currWave = 0;
		currWaveTime = 0;
		currMobTime = 0;
	}

	/**
	 * This quits the game.
	 */
	protected void quitGame(){
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to quit?", "Quit Game?",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(choice == JOptionPane.YES_OPTION){
			// Dispose of the frame, and end this thread.
			this.dispose();
			System.exit(0);
		}
	}

	/**
	 * This displays the menu.
	 */
	protected void goToMenu(){
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to go to the menu?", "Go to Menu?",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(choice == JOptionPane.YES_OPTION){
			inMenu = true;
			inHelp = false;
			gameStarted = false;
			inTowerPlacement = false;
			// Stop the timer, if it's running.
			if(timer != null)
				timer.stop();
		}
	}

	/**
	 * This displays the help menu.
	 */
	protected void displayHelpMenu(){
		if(timer != null)
			timer.stop();
		inHelp = true;
		inMenu = false;
		gameStarted = false;
		inTowerPlacement = false;
	}

	/**
	 * This calculates the movement costs of this board, starting from a given
	 * position, and this flood-fills the entire board, choosing the lowest
	 * value it can have for that specific tile. This method works recursively.
	 * 
	 * @param x
	 *            the current x position of this tile.
	 * @param y
	 *            the current y position of this tile.
	 * @param isInit
	 *            whether this is not the initial initialisation.
	 */
	private void calculateBoardCosts(int x, int y, boolean isInit){
		// If we're at the specified tile, then we fill the board with -1000 and
		// make this tile's movement cost 0.
		if(x == GRID_WIDTH - 2 && y == GRID_HEIGHT - 2 && !isInit){
			boardCosts = new int[GRID_WIDTH][GRID_HEIGHT];
			for(int[] asdf: boardCosts){
				Arrays.fill(asdf, -1000);
			}
			boardCosts[x][y] = 0;
			// We then go to all the tiles surrounding this tile.
			calculateBoardCosts(x + 1, y, true);
			calculateBoardCosts(x - 1, y, true);
			calculateBoardCosts(x, y + 1, true);
			calculateBoardCosts(x, y - 1, true);
			return;
		} else if(x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT){
			// This tile is out of bounds.
			return;
		} else if(boardCosts[x][y] >= 0){
			// Find the lowest non-negative cost of the surrounding tiles
			// (including itself) and set this tile's cost to the lowest.
			int temp = Integer.MAX_VALUE;
			if(x > 0 && boardCosts[x - 1][y] >= 0
					&& boardCosts[x - 1][y] + 1 < temp)
				temp = boardCosts[x - 1][y] + 1;
			if(y > 0 && boardCosts[x][y - 1] >= 0
					&& boardCosts[x][y - 1] + 1 < temp)
				temp = boardCosts[x][y - 1] + 1;
			if(x < boardCosts.length - 1 && boardCosts[x + 1][y] >= 0
					&& boardCosts[x + 1][y] + 1 < temp)
				temp = boardCosts[x + 1][y] + 1;
			if(y < boardCosts[x].length - 1 && boardCosts[x][y + 1] >= 0
					&& boardCosts[x][y + 1] + 1 < temp)
				temp = boardCosts[x][y + 1] + 1;
			if(temp < boardCosts[x][y])
				boardCosts[x][y] = temp;
		} else if(board[y * GRID_WIDTH + x].isCovered()){
			// If this is a tower, then we ignore it.
			boardCosts[x][y] = -1;
		} else if(!board[y * GRID_WIDTH + x].isTraverseable()){
			// If we can't get here anyways, ignore it.
			boardCosts[x][y] = -1;
		} else{
			// Find the lowest non-negative cost of the surrounding tiles and
			// set this tile's cost to the lowest.
			int temp = Integer.MAX_VALUE;
			if(x > 0 && boardCosts[x - 1][y] >= 0
					&& boardCosts[x - 1][y] + 1 < temp)
				temp = boardCosts[x - 1][y] + 1;
			if(y > 0 && boardCosts[x][y - 1] >= 0
					&& boardCosts[x][y - 1] + 1 < temp)
				temp = boardCosts[x][y - 1] + 1;
			if(x < boardCosts.length - 1 && boardCosts[x + 1][y] >= 0
					&& boardCosts[x + 1][y] + 1 < temp)
				temp = boardCosts[x + 1][y] + 1;
			if(y < boardCosts[x].length - 1 && boardCosts[x][y + 1] >= 0
					&& boardCosts[x][y + 1] + 1 < temp)
				temp = boardCosts[x][y + 1] + 1;
			boardCosts[x][y] = temp;
			// Traverse the surrounding tiles.
			calculateBoardCosts(x + 1, y, true);
			calculateBoardCosts(x - 1, y, true);
			calculateBoardCosts(x, y + 1, true);
			calculateBoardCosts(x, y - 1, true);
		}
	}

	/**
	 * This is the Tower Panel, where towers are selected from mouse input.
	 * 
	 * @author Kevin Choi, Kevin Lau and Collier Jiang
	 * @version January 20, 2010
	 */
	private class TowerPanel extends JPanel {
		private int startx;
		private int starty;
		private Image startGame;

		/**
		 * Create the Panel, and initialise the variables.
		 */
		public TowerPanel(){
			super();
			this.setPreferredSize(new Dimension(MAX_WIDTH, 64));
			// The position of the start game image.
			startGame = new ImageIcon("Images\\Buttons\\StartGame.png")
					.getImage();
			startx = MAX_WIDTH - startGame.getWidth(null) - 10;
			starty = (64 - startGame.getHeight(null)) / 2;
			// Add the mouse listener!
			this.addMouseListener(new MainListener());

			repaint();
		}

		/**
		 * Draw all the images / text that goes on this panel.
		 * 
		 * @param g
		 *            the graphics object to paint on.
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(inMenu){
				// Draw the menu!
				g.drawImage(menu, 0, 0, null);
			} else if(inHelp){
				// Draw the help screen!
				g.drawImage(help, 0, 0, null);
			} else if(inCutscene){
				// Black screen.
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, MAX_WIDTH, 64);
			} else if(!inMenu){
				// If we're out of the menu, draw the background.
				g.setColor(new Color(169, 204, 87));
				g.fillRect(0, 0, MAX_WIDTH, 64);
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, MAX_WIDTH - 1, 64 - 1);
				// Print the text that says money + lives.
				g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
				g.drawString("Towers: (click for description)", 10, 23);
				g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				g.drawString("Money: " + money, 10, 43);
				g.drawString("Lives: " + health, 200, 43);
				// Draw the backing for all the towers.
				for(int i = 0; i < TOWERS_PANEL.length; i++){
					g.drawImage(towerBack, 300 + (35 * i), 16, null);
					TOWERS_PANEL[i].paint(g, 300 + (35 * i), 16);
				}
				if(!gameStarted){
					// Draw the start game image, if we haven't started yet.
					g.drawImage(startGame, startx, starty, null);
				} else{
					// Draw the progress bar!
					g.drawRect(startx - 1, starty - 1, 101, startGame
							.getHeight(null) + 1);
					g.setColor(Color.ORANGE);
					g.fillRect(startx, starty,
							(int)(100 * (currWaveTime * 1.0 / TICKS_PER_WAVE)),
							startGame.getHeight(null));
					g.setColor(Color.BLACK);
					if(currWave < 49){
						g.drawString("Next Wave: ", startx - 115, starty
								+ startGame.getHeight(null) / 2);
						MOBS[currWave + 1].paint(g, startx - 40, starty);
					}
				}

			}

			repaint();
		}

		/**
		 * This is the mouse listener class (handles mouse events).
		 * 
		 * @author Kevin Choi, Kevin Lau and Collier Jiang
		 * @version January 20, 2010
		 */
		private class MainListener implements MouseListener {
			/**
			 * This handles when the mouse is clicked.
			 * 
			 * @param e
			 *            the MouseEvent.
			 */
			@Override
			public void mouseClicked(MouseEvent e){
				if(!inMenu){
					// Detect if the click was in the start game button.
					if(e.getX() >= startx
							&& e.getX() <= startx + startGame.getWidth(null)
							&& e.getY() >= starty
							&& e.getY() <= starty + startGame.getHeight(null)){
						if(!gameStarted)
							startGame();
						else if(mobsReleased >= NUMBER_MOBS_PER_WAVE){
							currWave++;
							mobsReleased = 0;
							currWaveTime = 0;
							currMobTime = TICKS_BETWEEN_MOBS + 1;
						}
					}
					// Otherwise, just set the tower to be placed.
					if(e.getY() >= 16 && e.getY() <= 48 && e.getX() <= 510
							&& e.getX() >= 300){
						if(money >= TOWERS_PANEL[(e.getX() - 300) / 35]
								.getInitCost()){
							towerFromPanel = (e.getX() - 300) / 35;
							inTowerPlacement = true;
						}
					}
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0){
			}

			@Override
			public void mouseExited(MouseEvent arg0){
			}

			@Override
			public void mousePressed(MouseEvent arg0){
			}

			@Override
			public void mouseReleased(MouseEvent arg0){
			}
		}
	}

	/**
	 * This is the description panel. This displays the descriptions of the
	 * towers, and the upgrade/sell buttons.
	 * 
	 * @author Kevin Choi, Kevin Lau and Collier Jiang
	 * @version January 20, 2010
	 */
	private class DescPanel extends JPanel {
		private Image upgrade;
		private Image sell;

		/**
		 * Create the Panel.
		 */
		public DescPanel(){
			super();
			this.setPreferredSize(new Dimension(MAX_WIDTH, 94));
			upgrade = new ImageIcon("Images\\Buttons\\Upgrade.png").getImage();
			sell = new ImageIcon("Images\\Buttons\\Sell.png").getImage();
			// Add the mouse listener!
			this.addMouseListener(new MainListener());
		}

		/**
		 * Draw all the text/images for this Panel.
		 * 
		 * @param g
		 *            the Graphics object to draw on.
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			if(inMenu){
				// Draw the menu if we're in the menu!
				g.drawImage(menu, 0, -576, null);
			} else if(inHelp){
				// Draw the help screen!
				g.drawImage(help, 0, -576, null);
			} else if(inCutscene){
				// Black screen.
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, MAX_WIDTH, 94);
			} else{
				// Set the color to nice and draw the background.
				g.setColor(new Color(169, 204, 87));
				g.fillRect(0, 0, MAX_WIDTH, 94);
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, MAX_WIDTH - 1, 94 - 1);
				// If we're placing towers, display the important stats about
				// that tower.
				if(inTowerPlacement){
					TOWERS_PANEL[towerFromPanel].paint(g, 10, 31);
					StringTokenizer st = new StringTokenizer(
							TOWER_DESC[towerFromPanel], "\n");
					g.drawString(st.nextToken(), 52, 14);
					g.drawString(st.nextToken(), 52, 31);
					g.drawString(st.nextToken(), 52, 48);
					g.drawString(st.nextToken(), 52, 65);
					g.drawString(st.nextToken(), 52, 82);
				} else if(selectedFromMain != null){
					// If we've selected a tower from the grid, we display the
					// information about that tower. (and the upgrade/sell if
					// applicable) If it's a Mob, we display HP and money!
					if(selectedFromMain instanceof Mob
							&& ((Mob)selectedFromMain).isDead()){
						selectedFromMain = null;
					} else{
						selectedFromMain.paint(g, 10, 31);
						if(selectedFromMain instanceof Mob){
							StringTokenizer st = new StringTokenizer(
									selectedFromMain.toString(), "\n");
							g.drawString(st.nextToken(), 52, 40);
							g.drawString(st.nextToken(), 52, 57);
						} else if(selectedFromMain instanceof Tower){
							Tower t = (Tower)selectedFromMain;
							StringTokenizer st = new StringTokenizer(t
									.currStats(), "\n");
							g.drawString(st.nextToken(), 100, 20);
							g.drawString(st.nextToken(), 100, 37);
							g.drawString(st.nextToken(), 100, 54);
							g.drawString(st.nextToken(), 100, 71);
							if(t.canUpgrade())
								g.drawImage(upgrade, 625, 10, null);
							g.drawImage(sell, 625, 50, null);
						}
					}
				}
			}
			repaint();
		}

		/**
		 * The class that handles mouse events for this panel.
		 * 
		 * @author Kevin Choi, Kevin Lau and Collier Jiang
		 * @version January 20, 2010
		 */
		private class MainListener implements MouseListener {
			/**
			 * The method that handles mouse clicks.
			 * 
			 * @param e
			 *            the MouseEvent.
			 */
			@Override
			public void mouseClicked(MouseEvent e){
				// Get the x and y values so we don't keep calling the method.
				int x = e.getX();
				int y = e.getY();
				if(inMenu){
					// If the information button is pressed, we display it.
					if(x >= 135 && x <= 377 && y >= -15 && y <= 15)
						displayHelpMenu();
				} else if(inHelp){
					if(x >= 453 && x <= 695 && y >= -10 && y <= 30)
						try{
							newGame();
						} catch(FileNotFoundException e1){
						}
					else if(x >= 453 && x <= 695 && y >= 42 && y <= 72)
						goToMenu();
				} else if(selectedFromMain != null){
					if(x >= 625 && x <= 700 && y >= 10 && y <= 45){
						// Handle upgrades. If we have the money to do so, we do
						// it. Otherwise, we don't.
						if(selectedFromMain instanceof Tower){
							doUpgrade();
						}
					} else if(x >= 625 && x <= 700 && y >= 50 && y <= 85){
						// We sell the tower for the given cost, and remove it
						// from the board.
						if(selectedFromMain instanceof Tower){
							sellTower();
						}
					}
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0){
			}

			@Override
			public void mouseExited(MouseEvent arg0){
			}

			@Override
			public void mousePressed(MouseEvent arg0){
			}

			@Override
			public void mouseReleased(MouseEvent arg0){
			}
		}
	}

	/**
	 * The GridPanel (or main panel).
	 * 
	 * @author Kevin Choi, Kevin Lau and Collier Jiang
	 * @version January 20, 2010
	 */
	private class MainPanel extends JPanel {
		private int xtower;
		private int ytower;

		/**
		 * This handles when the mouse is moved, but not clicked.
		 * 
		 * @author Kevin Choi, Kevin Lau and Collier Jiang
		 * @version January 20, 2010
		 */
		private class MainMotionListener implements MouseMotionListener {
			@Override
			public void mouseDragged(MouseEvent e){
			}

			/**
			 * This handles when the mouse is moved, but not held down.
			 * 
			 * @param e
			 *            the MouseEvent.
			 */
			@Override
			public void mouseMoved(MouseEvent e){
				// This just finds the tile to snap the image onto.
				if(inTowerPlacement){
					xtower = (e.getX() / TILE_SIZE) * TILE_SIZE;
					ytower = (e.getY() / TILE_SIZE) * TILE_SIZE;
				}
				repaint();
			}
		}

		/**
		 * This handles clicking events for this Panel.
		 * 
		 * @author Kevin Choi, Kevin Lau and Collier Jiang
		 * @version January 20, 2010
		 */
		private class MainListener implements MouseListener {
			/**
			 * This handles when the mouse is clicked.
			 * 
			 * @param e
			 *            the MouseEvent.
			 */
			public void mouseClicked(MouseEvent e){
				int x = e.getX();
				int y = e.getY();

				// 453, 566
				if(inMenu){
					if(x >= 135 && x <= 377 && y >= 424 && y <= 481)
						// Start a new game.
						try{
							newGame();
						} catch(FileNotFoundException e1){
						}
					else if(x >= 135 && x <= 377 && y >= 497 && y <= 527)
						// Display the help menu.
						displayHelpMenu();
				} else if(inHelp){
					if(x >= 453 && x <= 695 && y >= 502 && y < 532)
						// Start a new game.
						try{
							newGame();
						} catch(FileNotFoundException e1){
						}

				} else if(inTowerPlacement){
					// Find the tile that was clicked.
					int xtile = x / TILE_SIZE;
					int ytile = y / TILE_SIZE;
					Tile t = board[((ytile) * GRID_WIDTH) + (xtile)];
					// If this tile can be built on, doesn't already have a
					// tower, isn't out of bounds, and we have enough money for
					// it, then we build it.
					if(!t.isCovered()
							&& t.isTraverseable()
							&& !((ytile) == 0 && (xtile) == 0)
							&& !((ytile) >= GRID_HEIGHT - 3 && (xtile) >= GRID_WIDTH - 3)
							&& money >= TOWERS_PANEL[towerFromPanel]
									.getInitCost()){
						// This is just to handle specific types of Towers.
						// (toothbrush, toothpaste, tonguecleaner, etc.)
						if(TOWERS[towerFromPanel] instanceof ToothBrush)
							t.placeOver(new ToothBrush(
									(ToothBrush)TOWERS[towerFromPanel], (xtile)
											* TILE_SIZE, (ytile) * TILE_SIZE));
						else if(TOWERS[towerFromPanel] instanceof ToothPaste)
							t.placeOver(new ToothPaste(
									(ToothPaste)TOWERS[towerFromPanel], (xtile)
											* TILE_SIZE, (ytile) * TILE_SIZE));
						else if(TOWERS[towerFromPanel] instanceof ElecTB)
							t.placeOver(new ElecTB(
									(ElecTB)TOWERS[towerFromPanel], (xtile)
											* TILE_SIZE, (ytile) * TILE_SIZE));
						else if(TOWERS[towerFromPanel] instanceof TongueCleaner)
							t.placeOver(new TongueCleaner(
									(TongueCleaner)TOWERS[towerFromPanel],
									(xtile) * TILE_SIZE, (ytile) * TILE_SIZE));
						else if(TOWERS[towerFromPanel] instanceof MouthWash)
							t.placeOver(new MouthWash(
									(MouthWash)TOWERS[towerFromPanel], (xtile)
											* TILE_SIZE, (ytile) * TILE_SIZE));
						else if(TOWERS[towerFromPanel] instanceof BreathSpray)
							t.placeOver(new BreathSpray(
									(BreathSpray)TOWERS[towerFromPanel],
									(xtile) * TILE_SIZE, (ytile) * TILE_SIZE));
						inTowerPlacement = false;
						money -= TOWERS_PANEL[towerFromPanel].getInitCost();
						calculateBoardCosts(GRID_WIDTH - 2, GRID_HEIGHT - 2,
								false);
						// If this is blocking the path, as evidenced by
						// remaining -1000 in the board, then we remove that
						// tower again, give back the money, and let the player
						// place that tower again.
						LOOP: for(int i = 0; i < boardCosts.length; i++){
							for(int j = 0; j < boardCosts[i].length; j++){
								if(boardCosts[i][j] == -1000
										&& board[j * GRID_WIDTH + i]
												.isTraverseable()
										&& !board[j * GRID_WIDTH + i]
												.isCovered()){
									t.removeOver();
									inTowerPlacement = true;
									money += TOWERS_PANEL[towerFromPanel]
											.getInitCost();
									break LOOP;
								}
							}
						}
					}
				} else{
					// Otherwise, find out what was clicked on.
					Tile t = board[((y / TILE_SIZE) * GRID_WIDTH)
							+ (x / TILE_SIZE)];
					if(t.isCovered()){
						// If the tile was actually a tower, we set the selected
						// as that tower.
						Tower to = (Tower)(t.getOver());
						selectedFromMain = to;
					} else{
						// Otherwise, we find out what Mob was clicked on.
						for(Movable m: movingObjs)
							if(m instanceof Mob && ((Mob)m).contains(x, y))
								selectedFromMain = (Mob)m;
					}
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0){
			}

			@Override
			public void mouseExited(MouseEvent arg0){
			}

			@Override
			public void mousePressed(MouseEvent arg0){
			}

			@Override
			public void mouseReleased(MouseEvent arg0){
			}
		}

		/**
		 * Create and initialise this Panel.
		 */
		public MainPanel(){
			super();
			// Add the mouse listeners!
			this.addMouseListener(new MainListener());
			this.addMouseMotionListener(new MainMotionListener());
			this.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
		}

		/**
		 * Draw the grid, towers and mobs.
		 * 
		 * @param g
		 *            the Graphics object to paint on.
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(inMenu){
				// Draw the menu!
				g.drawImage(menu, 0, -64, null);
			} else if(inHelp){
				// Or draw the help screen?
				g.drawImage(help, 0, -64, null);
			} else if(inCutscene){
				// Black screen.
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
				// Draw the next cutscene text.
				StringTokenizer st = new StringTokenizer(CUTSCENES[cutscene],
						"\n");
				int count = 0;
				g.setFont(new Font("Times New Roman", Font.PLAIN, 17));
				g.setColor(Color.WHITE);
				while(st.hasMoreTokens()){
					g.drawString(st.nextToken(), 5, 15 + 20 * count);
					count++;
				}
				g.drawString("Press 'N' to continue", MAX_WIDTH - 200,
						MAX_HEIGHT - 10);
			} else{
				// Otherwise, draw the background
				g.drawImage(bg, 0, 0, null);
				// and each tile (towers too)
				for(Tile tile: board)
					tile.paint(g);
				// but if we're placing a tower, we have to tell the player
				// what can be placed on, right?
				if(inTowerPlacement){
					for(int x = 0; x < MAX_WIDTH; x += TILE_SIZE)
						for(int y = 0; y < MAX_HEIGHT; y += TILE_SIZE){
							// If the board is covered, then we display red.
							// Otherwise, we display green!
							if((board[(x / TILE_SIZE) + (y / TILE_SIZE)
									* GRID_WIDTH].isCovered())
									|| (!board[(x / TILE_SIZE)
											+ (y / TILE_SIZE) * GRID_WIDTH]
											.isTraverseable())
									|| (x / TILE_SIZE == 0 && y / TILE_SIZE == 0)
									|| (x / TILE_SIZE >= GRID_WIDTH - 3 && y
											/ TILE_SIZE >= GRID_HEIGHT - 3))
								g.drawImage(redImg, x, y, null);
							else
								g.drawImage(greenImg, x, y, null);
						}
					TOWERS_PANEL[towerFromPanel].paint(g, xtower, ytower);
				}
				// Finally, we can paint all the moving objects.
				for(Movable m: movingObjs)
					m.paint(g);
			}

			repaint();
		}
	}

	// This is also for the Timer, too.
	private static final long MINTIME = 40;
	private long lastTime;

	/**
	 * Start the game. (Initialises the timer, and other stuff.
	 */
	public void startGame(){
		lastTime = MINTIME;
		timer = new Timer((int)lastTime, new TickHandler());
		timer.start();
		gameStarted = true;
		// The first wave is always the easiest.
		currWave = 0;
		currMobTime = TICKS_BETWEEN_MOBS;
		// Calculate the board costs. Again.
		calculateBoardCosts(GRID_WIDTH - 2, GRID_HEIGHT - 2, false);
	}

	/**
	 * This is the tick handler. This controls how fast the game goes. Each tick
	 * is a minimum of 40 milliseconds, but isn't always. Each tick, everything
	 * moves one step.
	 * 
	 * @author Kevin Choi, Kevin Lau and Collier Jiang
	 * @version January 20, 2010
	 */
	private class TickHandler implements ActionListener {
		/**
		 * This is the action to do, when the timer fires.
		 * 
		 * @param e
		 *            the ActionEvent.
		 */
		@Override
		public void actionPerformed(ActionEvent e){
			// Set the delay to something closer to what the last delay was.
			// This tries to handle lag spikes and such.
			timer.setDelay(((lastTime > MINTIME)? (int)lastTime: (int)MINTIME));
			long start = System.nanoTime();

			// Copy the costs of the board, so we don't get synchronisation
			// errors.
			int[][] boardCostsCopy = new int[boardCosts.length][boardCosts[0].length];
			for(int i = 0; i < boardCosts.length; i++)
				for(int j = 0; j < boardCosts[i].length; j++)
					boardCostsCopy[i][j] = boardCosts[i][j];

			// This is the ArrayList of Moveable objects to remove after this
			// tick is finished.
			ArrayList<Movable> toRemove = new ArrayList<Movable>();

			// If we're on the last wave, just do nothing. For now.
			if(currWave >= LAST_WAVE - 1){
				if(movingObjs.isEmpty()){
					winTheGame();
				}
			} else{
				// If it's time for the next wave, send the next wave.
				if(currWaveTime >= TICKS_PER_WAVE){
					currWave++;
					mobsReleased = 0;
					currWaveTime = 0;
				}

				// Send out the next mob, if it's time.
				if(mobsReleased < NUMBER_MOBS_PER_WAVE
						&& currMobTime >= TICKS_BETWEEN_MOBS){
					// This will handle major bosses, and other bosses.
					if((currWave + 1) % 25 == 0){
						if(cutscene == 0 || cutscene == 2)
							inCutscene = true;
						// Only one major boss!
						mobsReleased = NUMBER_MOBS_PER_WAVE;
						movingObjs.add(new Mob(MOBS[currWave]));
					} else if((currWave + 2) % 25 == 0 && cutscene == 1){
						inCutscene = true;
					} else if((currWave + 1) % 5 == 0){
						if(mobsReleased % 4 == 0){
							// Only release a boss every 4 times.
							if(MOBS[currWave] instanceof SwarmMob){
								// If it's a swarm boss, then make it a swarm
								// boss.
								movingObjs.add(new SwarmMob(
										(SwarmMob)MOBS[currWave], true, 0));
							} else{
								movingObjs.add(new Mob(MOBS[currWave]));
							}
						}
					} else{
						// Otherwise, just add the monster.
						movingObjs.add(new Mob(MOBS[currWave]));
					}
					mobsReleased++;
					currMobTime = 0;
				}
			}
			// Make all the towers do their thing. (Depends on what the tower
			// is)
			for(Tile t: board)
				if(t.isCovered()){
					Tower to = t.getOver();
					to.doFunction(movingObjs);
					// Any projectiles produced will be added to the
					// movingObjs.
					ArrayList<Movable> projectilesToAdd = to.getProjectiles();
					movingObjs.addAll(projectilesToAdd);
				}

			// This is to hold the temporary spawns for each Spawn mob.
			ArrayList<SwarmMob> swarmSpawns = new ArrayList<SwarmMob>();
			for(Movable m: movingObjs){
				if(m instanceof Mob){
					Mob mob = (Mob)m;
					if(mob.isOnLastTile()){
						// If the mob reaches the last tile, you lose 1
						// life/health.
						toRemove.add(m);
						health--;
					}
					if(mob.needsMove())
						// Find the next move for this mob.
						mob.determineNextMove(boardCostsCopy);
				}
				if(m instanceof Projectile){
					// If it's a projectile, we then move it, and check if it
					// collides.
					Projectile pro = (Projectile)m;
					pro.hasCollided(movingObjs);
				}
				if(m.isDead()){
					// If the moving object is now used up/disabled, then we add
					// the money we get from it, if it's a mob, and remove it
					// from the movingObjs.
					if(m instanceof Mob){
						money += ((Mob)m).getMoney();
						if(m instanceof SwarmMob){
							// But if it splits, then we re-add the splitted
							// ones.
							swarmSpawns.addAll(((SwarmMob)m).getSplitted());
						}
					}
					toRemove.add(m);
				}
				m.move();
			}

			movingObjs.removeAll(toRemove);
			movingObjs.addAll(swarmSpawns);
			currMobTime++;
			currWaveTime++;

			// If you lost the game, you lose the game.
			if(health <= 0){
				loseGame();
			}
			lastTime = (System.nanoTime() - start) / 1000000;
			repaint();
			if(inCutscene)
				timer.stop();
		}
	}

	/**
	 * Display a "You've lost the game" message.
	 */
	public void loseGame(){
		// Stop the timer!
		timer.stop();
		// Display a message depending on how far the person has gotten.
		StringBuffer sb = new StringBuffer(
				"You have just lost the game.\nYou have done a");
		if(currWave < 10)
			sb.append(" poor");
		else if(currWave < 20)
			sb.append(" mediocre");
		else if(currWave < 30)
			sb.append(" good");
		else if(currWave < 40)
			sb.append("n admirable");
		else if(currWave < 50)
			sb.append(" great");
		sb.append(" job of helping the tooth fairy fight off ");
		sb.append(currWave);
		sb.append(" rounds of Tom Quaple’s mutant sweets, but they were just");
		sb.append(" too strong.\nThe tooth fairy was never seen again, and");
		sb.append(" Tom used his army of sweets to take over the world.");
		sb.append("\nWould you like to try again?");
		int choice = JOptionPane.showConfirmDialog(this, sb.toString(),
				"You lose.", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if(choice == JOptionPane.YES_OPTION){
			try{
				newGame();
			} catch(FileNotFoundException e){
			}
		} else{
			goToMenu();
		}
	}

	/**
	 * Display the winning game message.
	 */
	public void winTheGame(){
		timer.stop();
		// Display the final message. (It's a bit long)
		String finalMessage = "Just as Tom Quaple’s plaque monster form neared"
				+ " the tooth fairy’s fortress,\none final hit from a tower had"
				+ " reduced Tom’s health to zero, and he slowly fell.\n"
				+ "With an angry groan, the plaque monster shrunk down and me"
				+ "lted.\nThere was no explosion this time, and out of sheer "
				+ "relief, the tooth fairy quickly rushed out of her fortress"
				+ " to see what happened.\nThe dark puddle that used to be To"
				+ "m was motionless on the ground.\nCautiously, the tooth fai"
				+ "ry gathered the remains, and placed it in a jar before flyi"
				+ "ng off to dispose of it properly.\n\nCongratulations!  You "
				+ "have helped the tooth fairy stop Tom "
				+ "Quaple and his army of plaque monsters.\nPlay again?";
		int choice = JOptionPane.showConfirmDialog(this, finalMessage,
				"You've just won the game. I didn't think it was possible!",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(choice == JOptionPane.YES_OPTION)
			try{
				newGame();
			} catch(FileNotFoundException e){
			}
		else{
			goToMenu();
		}
	}

	/**
	 * Exit the tower placement mode.
	 */
	private void exitTowerPlacement(){
		inTowerPlacement = false;
		this.towerFromPanel = 0;
	}

	/**
	 * Do an upgrade on the selected tower.
	 */
	private void doUpgrade(){
		Tower t = (Tower)selectedFromMain;
		if(t.canUpgrade() && money >= t.getCostToUpgrade()){
			money -= t.getCostToUpgrade();
			t.doUpgrade();
		}
	}

	/**
	 * Sell the the selected tower.
	 */
	private void sellTower(){
		Tower t = (Tower)selectedFromMain;
		if(t instanceof TongueCleaner)
			board[((int)(t.getX() + TILE_SIZE) / TILE_SIZE)
					+ (((int)(t.getY()) / TILE_SIZE) * GRID_WIDTH)]
					.removeOver();
		else
			board[((int)(t.getX()) / TILE_SIZE)
					+ (((int)(t.getY()) / TILE_SIZE) * GRID_WIDTH)]
					.removeOver();
		if(gameStarted)
			money += t.getSellPrice();
		else
			money += t.getFullPrice();
		selectedFromMain = null;
	}

	/**
	 * @param args
	 *            the command-line arguments.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		MainFrame mainFrame = new MainFrame("Candy Tower Defence");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(800, 600));
		mainFrame.setResizable(false);
	}
}
