package com.gmail.pkr4mer.glooputil.addon;

import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

import javax.swing.*;
import java.awt.*;

/**
 * Created by peter on 3/6/14.
 */
public class ElementWatcher extends BehaviourScript
{
    private JFrame window;
    private Scene scene;
    private JList<String> indicator;
    private JScrollPane scrollbar;

    private int sinceUpdate = 0;

    public ElementWatcher(Scene pScene)
    {
        scene = pScene;
        window = new JFrame("GloopUtil - Element Watcher");
        Dimension d = window.getSize();
        d.width = 200;
        window.setSize(d);
        scrollbar = new JScrollPane();
        indicator = new JList<>();
        scrollbar.setViewportView(indicator);
        window.getContentPane().add(scrollbar);
        window.pack();
        window.setVisible(true);
        indicator.setListData(scene.debug().split("\n"));

    }

    @Override
    public void fixedUpdate()
    {
        sinceUpdate++;
        if(sinceUpdate >= 200)
        {
            int i = indicator.getSelectedIndex();
            indicator.setListData(scene.debug().split("\n"));
            indicator.setSelectedIndex(i);
            sinceUpdate = 0;
        }
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.HIGH;
    }
}
