Ext.define('MusicSearch.view.Viewport', {
	extend: 'Ext.Viewport',

	layout: 'fit',

	initComponent: function() {
		var me = this;
//
//		var tabCloseMenu = Ext.create('Ext.ux.TabCloseMenu');
//		tabCloseMenu.closeTabText = i18n.tabclosemenu_close;
//		tabCloseMenu.closeOthersTabsText = i18n.tabclosemenu_closeother;
//		tabCloseMenu.closeAllTabsText = i18n.tabclosemenu_closeall;
//
//		me.items = [ Ext.create('Changelog.view.navigation.Header', {
//			region: 'north',
//			split: false
//		}), {
//			region: 'center',
//			xtype: 'tabpanel',
//			itemId: 'tabPanel',
//			plugins: [ Ext.create('Ext.ux.TabReorderer'), tabCloseMenu ],
//			plain: true
//		}, Ext.create('Changelog.view.navigation.SideBar', {
//			region: 'west',
//			width: 180
//		}) ];

		me.callParent(arguments);
	}

});