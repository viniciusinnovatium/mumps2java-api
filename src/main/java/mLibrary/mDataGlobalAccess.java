package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyToLikeQuery;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyWithoutVarName;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateSubs;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateTableName;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.MetadataDAO;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocator;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocatorException;
import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
import br.com.innovatium.mumps2java.metadatacache.MetadataCache;
import br.com.innovatium.mumps2java.metadatacache.MetadataCacheChangeTrigger;

public class mDataGlobalAccess extends mDataAccess {
	private final MetadataDAO dao;
	private final MetadataCache metadataCache = MetadataCache.getCache();

	private final MetadataCacheChangeTrigger changeTrigger;
	
	public mDataGlobalAccess(mVariables mVariables) {
		super(mVariables, DataStructureUtil.GLOBAL);
		try {
			dao = ServiceLocator.locate(MetadataDAO.class);
			changeTrigger = ServiceLocator.locate(MetadataCacheChangeTrigger.class);
		} catch (ServiceLocatorException e) {
			throw new IllegalArgumentException(
					"Fail to create data access object", e);
		}
	}

	public Object get(Object... subs) {
		Object value = metadataCache.get(subs);
		if (value != null) {
			return value;
		}
		if (metadataCache.isQueried(subs)) {
			return null;
		}
		final String tableName = generateTableName(subs);
		value = dao.find(tableName, generateKeyWithoutVarName(subs));
		if (value != null) {
			metadataCache.set(subs, value);
		}
		if (!metadataCache.isQueried(subs)) {
			metadataCache.addQueried(subs);
		}
		return value;
	}

	/*
	 * This method was create to support lastVar function and should not remove.
	 */
	public Object[] getCurrentSubs() {
		return currentSubs;
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public void set(Object value) {
		if (currentSubs != null && value != null) {
			metadataCache.set(currentSubs, value.toString());
			//changeTrigger.insert(currentSubs, value);
		}
	}

	public void stacking(Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void stackingExcept(Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void unstacking() {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void kill(Object[] subs) {
		currentSubs = null;
		metadataCache.kill(subs);
	}

	@Override
	public void merge(Object[] dest, Object[] orig) {
		metadataCache.merge(dest, orig);
	}

	public int data(Object... subs) {
		currentSubs = subs;

		populateCache(false);
		return metadataCache.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		this.currentSubs = subs;

		populateCache(true);
		return metadataCache.order(subs);
	}

	public Object order(Object... subs) {
		return order(subs, 1);
	}

	private void populateCache(boolean isOrder) {
		if (!metadataCache.isQueried(currentSubs)) {
			findDataOnDisk(isOrder);
			metadataCache.addQueried(currentSubs, isOrder);
		}
	}

	private void findDataOnDisk(boolean isOrder) {

		String tableName = generateTableName(currentSubs);
		Map<String, String> map = null;
		if (isOrder) {
			map = dao.like(tableName, generateKeyToLikeQuery(currentSubs));
		} else {
			map = dao.like(tableName, generateKeyWithoutVarName(currentSubs));
		}

		if (map != null) {
			Set<Entry<String, String>> result = map.entrySet();
			for (Entry<String, String> entry : result) {
				// Here we have to include variable or table name into the key
				// because this is part of the subscripts.
				metadataCache.set(generateSubs(tableName, entry.getKey()),
						entry.getValue());
			}
		}

		Object value = get(currentSubs);
		if (value != null) {
			metadataCache.set(currentSubs, value);
		}
	}

	@Override
	public void stackingBlock(int indexBlock, Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable into a block is not supported to access data on disk");
	}

	@Override
	public void stackingExceptBlock(int indexBlock, Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable into a block is not supported to access data on disk");
	}

	@Override
	public void unstackingBlock(int indexBlock) {
		throw new UnsupportedOperationException(
				"Stacking variable into a block is not supported to access data on disk");
	}
}
