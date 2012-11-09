package de.uniks.jism.gui.table;

import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.TableItem;

/*
Copyright (c) 2012, Stefan Lindel
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
   must display the following acknowledgement:
   This product includes software developed by Stefan Lindel.
4. Neither the name of contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY STEFAN LINDEL ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL STEFAN LINDEL BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
public class ColumnNotification extends Column{
	private EditingSupport editingsupport;

	public ColumnNotification(){
		
	}
	public ColumnNotification(String label, int width){
		super(label, width);
	}
	
	public ColumnNotification(String label, int width, String attrName){
		super(label, width, attrName);
	}
	
	public ColumnNotification(String label, int width, String attrName, boolean edit){
		super(label, width, attrName, edit);
	}
	
	
	public ColumnNotification(String label, int width, String attrName, String regEx){
		super(label, width, attrName, regEx);
	}
	public ColumnNotification(String label, int width, String attrName, String regEx, String editColumn){
		super(label, width, attrName, regEx, editColumn);
	}
	
	public void updateTableViewer(ViewerCell cell){
		
	}
	
	public void setEditingSupport(EditingSupport value) {
		this.editingsupport=value;
	}
	public EditingSupport getEditingSupport() {
		return this.editingsupport;
	}
	
	public boolean isEditingSupport() {
		return editingsupport!=null;
	}
	
	public void setSelection(TableComponent tableComponent, TableItem item, int x, int y){
		super.setSelection(x, y);
	}
}
