package cz.speedygonzales;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.TimerTask;

@Slf4j
public class ApplicationGui {

    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = SCREEN_WIDTH;

    private static final int GAP = 1;
    private static final int WIDTH = 60;
    private static final int HEIGHT = WIDTH;
    private static final int SLIDER_MIN = 100;
    private static final int SLIDER_MAX = 2_000;
    private static final int SLIDER_DEFAULT = 1_000;

    private JFrame applicationFrame;

    private JPanel cellPanel;

    private JSlider tickPerMillis;

    private GameOfLifeWorld world;

    private java.util.Timer timer;

    private boolean timerIsRunning = false;


    private ApplicationGui() {

        world = new GameOfLifeWorld(WIDTH, HEIGHT);

        initializeGui();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(ApplicationGui::run);
    }

    private static void run() {
        try {
            ApplicationGui window = new ApplicationGui();
            window.applicationFrame.setVisible(true);
        } catch (Exception e) {
            log.error("Problem running application.", e);

        }
    }


    private void initializeGui() {

        initMainApplicationFrame();

        initGameSection(world.getCells());

        initControlSection();
    }

    private void initGameSection(Cell[][] cells) {

        cellPanel = new JPanel(new GridLayout(WIDTH, HEIGHT));
        cellPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                JPanel panel = new CellPanel(aCell);
                cellPanel.add(panel);
            }
        }

        applicationFrame.getContentPane().add(cellPanel, BorderLayout.CENTER);
        applicationFrame.validate();
    }

    private void initControlSection() {

        JPanel controlPanel = new JPanel(new GridLayout(0, 4));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startTimer();
        });

        controlPanel.add(startButton);

        tickPerMillis = new JSlider(JSlider.HORIZONTAL,
                SLIDER_MIN, SLIDER_MAX, SLIDER_DEFAULT);
        tickPerMillis.setPaintTicks(true);
        tickPerMillis.setMajorTickSpacing(SLIDER_MAX / 10);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(SLIDER_MIN, new JLabel(SLIDER_MIN + "ms"));
        labelTable.put(SLIDER_MAX, new JLabel(SLIDER_MAX + "ms"));
        tickPerMillis.setLabelTable(labelTable);
        tickPerMillis.setPaintLabels(true);

        tickPerMillis.addChangeListener(e -> {
            stopTimer();
            startTimer();
        });

        controlPanel.add(tickPerMillis);


        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            stopTimer();
        });

        controlPanel.add(stopButton);

        JButton tickButton = new JButton("Tick");
        tickButton.addActionListener(e -> {
            tick();
        });

        controlPanel.add(tickButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            applicationFrame.setVisible(false);
            applicationFrame.dispose();
        });

        controlPanel.add(closeButton);

        applicationFrame.getContentPane().add(controlPanel, BorderLayout.PAGE_END);
    }

    private void startTimer() {
        if (!timerIsRunning) {

            timerIsRunning = true;

            TimerTask task = new TimerTask() {
                public void run() {
                    tick();
                }
            };
            timer = new java.util.Timer("Timer");

            timer.schedule(task, tickPerMillis.getValue(), tickPerMillis.getValue());
        }
    }

    private void tick() {
        Cell[][] tick = world.updateWorldToNextIteration();

        applicationFrame.getContentPane().remove(cellPanel);
        initGameSection(tick);
    }

    private void stopTimer() {
        timerIsRunning = false;

        if(timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void initMainApplicationFrame() {

        applicationFrame = new JFrame();
        applicationFrame.setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        applicationFrame.getContentPane().setLayout(new BorderLayout());
    }
}
