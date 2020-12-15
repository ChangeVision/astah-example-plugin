/*
 * パッケージ名は、生成したプラグインのパッケージ名よりも下に移してください。
 * プラグインのパッケージ名=> com.example
 *   com.change_vision.astah.extension.plugin => X
 *   com.example                              => O
 *   com.example.internal                     => O
 *   learning                                 => X
 */
package com.example.internal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.project.ProjectEvent;
import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;

public class AllClassesExtraTabView extends JPanel implements IPluginExtraTabView, ProjectEventListener {

	protected AllClassesTableView allClassesTableView;

	
    public AllClassesExtraTabView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(createTablePane(), BorderLayout.CENTER);
        addProjectEventListener();
    }
    
	private void addProjectEventListener() {
		try {
			ProjectAccessor projectAccessor = ProjectAccessorFactory.getProjectAccessor();
			projectAccessor.addProjectEventListener(this);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
    
	private Container createTablePane() {
    	allClassesTableView = new AllClassesTableView();
        JScrollPane pane = new JScrollPane(allClassesTableView);
        return pane;
    }
    
    @Override
    public void projectChanged(ProjectEvent e) {
    	showAllClassesInTableView();
    }

    @Override
    public void projectClosed(ProjectEvent e) {
    }

     @Override
    public void projectOpened(ProjectEvent e) {
    }

	@Override
	public void addSelectionListener(ISelectionListener listener) {
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getDescription() {
		return "Show all classes in this table.";
	}

	@Override
	public String getTitle() {
		return "Class List Table";
	}
	
	public void activated() {
		
	}

	public void deactivated() {
		
	}

	public void showAllClassesInTableView() {
		allClassesTableView.updateTable();
	}
}