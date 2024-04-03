/*
 * パッケージ名は、生成したプラグインのパッケージ名よりも下に移してください。
 * プラグインのパッケージ名=> com.example
 *   com.change_vision.astah.extension.plugin => X
 *   com.example                              => O
 *   com.example.internal                     => O
 *   learning                                 => X
 */
package com.example.actions;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class CountClassAction implements IPluginActionDelegate {
    public Object run(IWindow window) throws UnExpectedException {
        try {
        	AstahAPI api = AstahAPI.getAstahAPI();
            ProjectAccessor projectAccessor = api.getProjectAccessor();
            IModel iCurrentProject = projectAccessor.getProject();
            List<IClass> classeList = new ArrayList<IClass>();
            getAllClasses(iCurrentProject, classeList);
            JOptionPane.showMessageDialog(window.getParent(), 
            		"There are " + classeList.size() + " classes.");
        } catch (ProjectNotFoundException e) {
            String message = "Please open a project";
            JOptionPane.showMessageDialog(window.getParent(), message, 
            		"Warning", JOptionPane.WARNING_MESSAGE); 
            throw new CalculateUnExpectedException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), 
            		"Exception occured", "Alert", JOptionPane.ERROR_MESSAGE); 
            throw new UnExpectedException();
        }
        return null;
    }
    
    public class CalculateUnExpectedException extends UnExpectedException {
    } 

    private void getAllClasses(INamedElement element, List<IClass> classList) throws ClassNotFoundException, ProjectNotFoundException {
        if (element instanceof IPackage) {
            for(INamedElement ownedNamedElement : ((IPackage)element).getOwnedElements()) {
                getAllClasses(ownedNamedElement, classList);
            }
        } else if (element instanceof IClass) {
            classList.add((IClass)element);
            for(IClass nestedClasses : ((IClass)element).getNestedClasses()) {
                getAllClasses(nestedClasses, classList);
            }
        }
    }
}