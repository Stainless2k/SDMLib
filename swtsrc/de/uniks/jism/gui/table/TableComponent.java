package de.uniks.jism.gui.table;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.sdmlib.serialization.IdMap;
import org.sdmlib.serialization.interfaces.PeerMessage;

import swing2swt.layout.BorderLayout;
import de.uniks.jism.gui.TableList;

public class TableComponent extends Composite implements Listener,
		PropertyChangeListener {
	public static final int FIXEDBROWSERLEFT = 1;
	public static final int BROWSER = 2;
	private Text searchText;
	private ArrayList<TableColumnView> columns = new ArrayList<TableColumnView>();
	private Cursor defaultCursor = new Cursor(Display.getDefault(),
			SWT.CURSOR_ARROW);
	private Cursor handCursor = new Cursor(Display.getDefault(),
			SWT.CURSOR_HAND);
	private TableItem activeItem;

	private TableViewer tableViewer;
	private TableViewer fixedTableViewerLeft;

	private boolean isToolTip;
	private Composite tableComposite;
	private TableSyncronizer tableSyncronizer;
	private String property;
	private Menu mnuColumns;

	protected TableList list;
	protected PeerMessage source;
	protected int additionKey;
	
	
	public static final String EMPTYCOLUMN = "empty";

	public TableComponent(Composite parent, int style) {
		super(parent, style);

		createContent();
	}

	public void createContent() {
		tableComposite = new Composite(this, SWT.NONE | SWT.FILL);
		tableComposite.setLayoutData(BorderLayout.CENTER);
		tableComposite.setLayout(new BorderLayout(0, 0));

		tableViewer = new TableViewer(tableComposite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		tableViewer.setLabelProvider(new TableLabelProvider());
		
		Menu headerMenu = new Menu(getShell(), SWT.POP_UP);
		MenuItem columnsMenue = new MenuItem(headerMenu, SWT.CASCADE);
		columnsMenue.setText("Columns");
		mnuColumns = new Menu(getShell(), SWT.DROP_DOWN);
		columnsMenue.setMenu(mnuColumns);

		table.setMenu(headerMenu);

		table.addListener(SWT.KeyDown, this);
		table.addListener(SWT.MouseMove, this);
		table.addListener(SWT.MouseUp, this);
		table.addListener(SWT.MouseExit, this);

		setLayout(new BorderLayout(0, 0));
		
		
	}

	public void addColumn(Column column) {
		if (column.getBrowserId() == FIXEDBROWSERLEFT) {
			setVisibleFixedColumns(true);
		}
		this.columns.add(new TableColumnView(this, column, mnuColumns));
		if (column.getAltAttribute() != null) {
			if (!isToolTip) {
				isToolTip = true;
				if(fixedTableViewerLeft!=null){
					ColumnViewerToolTipSupport.enableFor(fixedTableViewerLeft,
							ToolTip.NO_RECREATE);
				}
			}
		}
	}
	
	public void removeColumn(Column column) {
		for(TableColumnView item : this.columns){
			if(item.getColumn().equals(column)){
				removeColumn(item);
			}
		}
	}
	
	
	public void removeColumn(TableColumnView column) {
		if(this.columns.remove(column)){
			column.disposeColumn();
		}
	}

	public TableViewer getBrowserView(int browser) {
		if (browser == FIXEDBROWSERLEFT) {
			return fixedTableViewerLeft;
		} else {
			return tableViewer;
		}
	}

	public void setVisibleFixedColumns(boolean visible) {
		if (fixedTableViewerLeft != null && !visible) {
			for (TableColumnView item : columns) {
				if (item.getColumn().getBrowserId() == FIXEDBROWSERLEFT) {
					item.setVisible(false);
				}
			}

			fixedTableViewerLeft.getTable().dispose();
			fixedTableViewerLeft = null;
		} else if (fixedTableViewerLeft == null && visible) {
			fixedTableViewerLeft = new TableViewer(tableComposite, SWT.BORDER
					| SWT.FULL_SELECTION | SWT.MULTI | SWT.NO_SCROLL | SWT.FILL);
			Table table_fixedElements = fixedTableViewerLeft.getTable();
			table_fixedElements.setLayoutData(BorderLayout.WEST);
			table_fixedElements.setHeaderVisible(true);
			table_fixedElements.setLinesVisible(true);
			tableSyncronizer = new TableSyncronizer(this,
					fixedTableViewerLeft.getTable(), tableViewer.getTable());
			tableViewer.getTable().addMouseWheelListener(tableSyncronizer);
			tableViewer.getTable().addListener(SWT.Selection, tableSyncronizer);
			if(tableViewer.getTable().getVerticalBar()!=null){
			tableViewer.getTable().getVerticalBar()
					.addListener(SWT.Selection, tableSyncronizer);
			}
			for (TableColumnView item : columns) {
				if (item.getColumn().getBrowserId() == FIXEDBROWSERLEFT) {
					item.setVisible(true);
				}
			}
		}
	}

	public void setKeyListener(KeyListener listener) {
		searchText.addKeyListener(listener);
	}

	public void addControl(Control control) {
		control.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				3, 1));
	}

	public boolean finish(TableViewer viewer, int browserid) {
		ArrayList<String> items = new ArrayList<String>();
		for (TableColumnView view : columns) {
			if (view.getColumn().getBrowserId() == browserid) {
				if (view.getColumn().getAttrName() != null) {
					items.add(view.getColumn().getAttrName());
				} else if (view.getColumn().getItem() != null) {
					items.add(EMPTYCOLUMN);
				}
			}
		}

		for (TableColumnView view : columns) {
			Column columnConfig = view.getColumn();
			// if(columnConfig.getRegEx()!=null||columnConfig.getAltAttribute()!=null){
			view.getTableViewerColumn().setLabelProvider(
					new TableCellLabelProvider(columnConfig));
			// }
		}
		return true;
	}

	public void refresh(PeerMessage object) {
		ArrayList<String> refreshColumns = new ArrayList<String>();
		for (TableColumnView tableColumnView : columns) {
			refreshColumns.add(tableColumnView.getColumn().getAttrName());
		}
		if (fixedTableViewerLeft != null) {
			fixedTableViewerLeft.update(object,
					refreshColumns.toArray(new String[refreshColumns.size()]));
		}
		if (tableViewer != null) {
			tableViewer.update(object,
					refreshColumns.toArray(new String[refreshColumns.size()]));
		}

	}

	public void refresh() {
		if (fixedTableViewerLeft != null) {
			fixedTableViewerLeft.refresh();
		}
		tableViewer.refresh();
		Object listValue = list.get(property);
		if (listValue instanceof Collection<?>) {
			Collection<?> listItems = (Collection<?>) listValue;
			for (Iterator<?> i = listItems.iterator(); i.hasNext();) {
				Object item = i.next();
				if (item instanceof PeerMessage) {
					propertyChange(new PropertyChangeEvent(list, property,
							null, item));
				}
			}
		}
	}

	public boolean finishDataBinding(TableList item, String searchProperties) {
		return finishDataBinding(item, TableList.PROPERTY_ITEMS, searchProperties);
	}

	public boolean finishDataBinding(PeerMessage item, String property,
			String searchProperties) {

		if (fixedTableViewerLeft != null) {
			finish(fixedTableViewerLeft, FIXEDBROWSERLEFT);
		}

		finish(tableViewer, BROWSER);
		
		this.list = new TableList();
		this.list.addPropertyChangeListener(this);
		
		this.source=item;
		this.property = property;
		
		new UpdateSearchList(this, source);
		
//		tableViewer.setContentProvider(new IStructuredContentProvider() {
//			
//			@Override
//			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
//				
//			}
//			
//			@Override
//			public void dispose() {
//				
//			}
//
//			@Override
//			public Object[] getElements(Object inputElement) {
//				return new Object[]{};
//			}
//		});
//		tableViewer.setInput(list);
		return true;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public Text getSearchField() {
		return searchText;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt != null) {
			if (list.equals(evt.getSource())) {
				if (evt.getPropertyName().equals(property)) {
					if (evt.getOldValue() == null && evt.getNewValue() != null) {
						// ADD a new Item
						if (fixedTableViewerLeft != null) {
							fixedTableViewerLeft.add(evt.getNewValue());
						}
						if (tableViewer != null) {
							tableViewer.add(evt.getNewValue());
						}
					} else if (evt.getOldValue() != null
							&& evt.getNewValue() == null) {
						if (fixedTableViewerLeft != null) {
							fixedTableViewerLeft.remove(evt.getOldValue());
						}
						if (tableViewer != null) {
							tableViewer.remove(evt.getOldValue());
						}
					}
				}
			}else{
				// Must be an update
				if (fixedTableViewerLeft != null) {
					fixedTableViewerLeft.update(evt.getSource(),
							new String[] { evt.getPropertyName() });
				}
				if (tableViewer != null) {
					tableViewer.update(evt.getSource(),
							new String[] { evt.getPropertyName() });
				}
			}
		}
	}

	@Override
	public void handleEvent(Event event) {
		Point pt = new Point(event.x, event.y);

		TableItem currentItem = tableViewer.getTable().getItem(pt);
		if(SWT.MouseMove == event.type | SWT.MouseUp == event.type | SWT.MouseExit == event.type){
			if (SWT.MouseUp == event.type) {
				ViewerCell cell = getTableViewer().getCell(pt);
				if (cell != null) {
					int columnIndex = cell.getColumnIndex();
					int offset = 0;
					if (fixedTableViewerLeft != null) {
						offset = fixedTableViewerLeft.getTable().getColumnCount();
					}
					TableColumnView tableColumnView = columns.get(columnIndex
							+ offset);
					if (tableColumnView != null) {
						Composite parent = this;
						int x = event.x;
						int y = event.y;
						while (parent != null) {
							x += parent.getBounds().x;
							y += parent.getBounds().y;
							parent = parent.getParent();
						}
						tableColumnView.getColumn().setSelection(this, currentItem, x, y);
					}
					// setSelection(currentItem, cell, columnIndex);
				}
			}
			if (currentItem == null || currentItem.isDisposed()) {
				tableViewer.getTable().setCursor(defaultCursor);
			} else {
				boolean activ = false;
				for (int i = 0; i < columns.size(); i++) {
					TableColumnView tableViewerColumn = columns.get(i);
					Rectangle positem = currentItem.getBounds(i);
					if (positem != null) {
						if (event.x > positem.x
								&& event.x < (positem.x + positem.width)) {
							if (tableViewerColumn.getColumn().isEditingSupport()) {
								this.activeItem = currentItem;
								activ = true;
								TableColumnLabelProvider activeCell = tableViewerColumn
										.getTableProvider();
								Color color = activeCell.getForgroundColorActiv();
								if (color != null) {
									this.activeItem.setForeground(i, color);
								}
								color = activeCell.getBackgroundColorActiv();
								if (color != null) {
									this.activeItem.setBackground(i, color);
								}
								tableViewer.getTable().setCursor(handCursor);
							}
						}
					}
				}
				if (!activ) {
					tableViewer.getTable().setCursor(defaultCursor);
				}
			}
		}else if (SWT.KeyDown == event.type) {
			if (event.keyCode == SWT.CTRL || (event.stateMask & SWT.CONTROL) != 0){
				this.additionKey = SWT.CTRL;
				if(event.keyCode=='a'){
					// Select all
					TableComponent.this.selectAll();
				}
			}else if(event.keyCode == SWT.SHIFT || (event.stateMask & SWT.SHIFT) != 0) {
				this.additionKey = SWT.ALT;
			} else {
				this.additionKey = 0;
			}
		}
	}
	
	protected void selectAll() {
		int count=tableViewer.getTable().getItemCount();
		int[] array=new int[count];
		for(int i=0;i<count;i++){
			array[i]=i;
		}
		tableViewer.getTable().setSelection(array);
	}

	public int getTableItemCount() {
		return tableViewer.getTable().getItemCount();
	}

	public Table getTable() {
		return tableViewer.getTable();
	}

	public void onResizeColumn(TableColumn column) {
		for (TableColumnView item : columns) {
			if (item.getTableColumn() == column) {
				 onResizeColumn(item);
			}
		}
	}

	public void onResizeColumn(TableColumnView item) {
		if (item.getColumn().getBrowserId() == FIXEDBROWSERLEFT) {
			int size = 0;
			for (TableColumnView view : columns) {
				if (view.getColumn().getBrowserId() == FIXEDBROWSERLEFT) {
					if (view.getColumn().isVisible()) {
						size += view.getColumn().getWidth();
					}
				}
			}
			if (size == 0) {
				setVisibleFixedColumns(false);
			} else {
				setVisibleFixedColumns(true);
			}
			tableComposite.layout();
		}
	}

	public void onVisibleColumn(Column column, boolean value) {
		if(column.getBrowserId()==FIXEDBROWSERLEFT){
			if(value){
				setVisibleFixedColumns(true);
			}
		}
	}
	
	public void removeSelectionItems() {
		TableItem[] selectionItems = getTable().getSelection();
		
		if (selectionItems.length > 0) {
			for (TableItem tableItem : selectionItems) {
				PeerMessage item = (PeerMessage) tableItem.getData();
				list.set(property+IdMap.REMOVE, item);
				source.set(property+IdMap.REMOVE, item);
			}
		}
	}

	public TableList getList() {
		return list;
	}
}