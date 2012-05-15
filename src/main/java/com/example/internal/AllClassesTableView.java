/*
 * パッケージ名は、生成したプラグインのパッケージ名よりも下に移してください。
 * プラグインのパッケージ名=> com.example
 *   com.change_vision.astah.extension.plugin => X
 *   com.example                              => O
 *   com.example.internal                     => O
 *   learning                                 => X
 */
package com.example.internal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AllClassesTableView extends JTable {
	
	protected ProjectNameTableModel tableModel;
	private String fullClassNameColumn = "Full Class Name";
	
	public AllClassesTableView() {
		initTable();
	}

	private void initTable() {

		tableModel = new ProjectNameTableModel();
		setModel(tableModel);
		
		this.setName("ProjectNameTable");
		getColumn(fullClassNameColumn).setPreferredWidth(300);
		
		 setRowSelectionAllowed(true);
	     setColumnSelectionAllowed(false);
	     getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     getSelectionModel().addListSelectionListener(this);
	     setHeader();
	}
	
	private void setHeader() {
		JTableHeader hdr = new JTableHeader(getColumnModel());
        setTableHeader(hdr);
	}

	public void updateTable() {
		try {
            
			ProjectAccessor projectAccessor = ProjectAccessorFactory.getProjectAccessor();
			IModel iCurrentProject = projectAccessor.getCurrentProject();
			List<IClass> classList = new ArrayList<IClass>();
	    	getAllClasses(iCurrentProject, classList);
	    	
	    	if (classList.size() == 0) {
	    		tableModel.setNumRows(0);
	    		return;
	    	}
	    	tableModel.setNumRows(classList.size());
	    	
            String[] rowDatas = new String[classList.size()];
            int rowIndex = 0;
            for(IClass iClass : classList) {
            	setValueAt(iClass.getFullName("::"), rowIndex, 0);
            	rowIndex++;
            }
			
			clearSelection();
		} catch (ClassNotFoundException e) {
			return;
		} catch (ProjectNotFoundException e) {
			return;
		}
	}
        
	class ProjectNameTableModel extends DefaultTableModel {

		ProjectNameTableModel() {
			super();
			setColumnCount(0);
			addColumn(fullClassNameColumn);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	
	private void getAllClasses(INamedElement iNamedElement, List<IClass> classList) throws ClassNotFoundException, ProjectNotFoundException {
        if (iNamedElement instanceof IPackage) {
        	for(INamedElement ownedNamedElement : ((IPackage)iNamedElement).getOwnedElements()) {
        		getAllClasses(ownedNamedElement, classList);
        	}
        } else if (iNamedElement instanceof IClass) {
        	classList.add((IClass)iNamedElement);
        	for(IClass nestedClasses : ((IClass)iNamedElement).getNestedClasses()) {
        		getAllClasses(nestedClasses, classList);
        	}
        }
	}
}
