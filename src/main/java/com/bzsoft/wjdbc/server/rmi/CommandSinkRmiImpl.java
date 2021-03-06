package com.bzsoft.wjdbc.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.Unreferenced;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzsoft.wjdbc.command.Command;
import com.bzsoft.wjdbc.rmi.CommandSinkRmi;
import com.bzsoft.wjdbc.server.command.CommandProcessor;

public class CommandSinkRmiImpl implements CommandSinkRmi, Unreferenced {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandSinkRmiImpl.class);

	private final CommandProcessor	processor;

	public CommandSinkRmiImpl(final CommandProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void unreferenced() {
		LOGGER.info("Remote object unreferenced");
	}

	@Override
	public long connect(final String url, final Properties props, final Properties clientInfo) throws SQLException, RemoteException {
		return processor.createConnection(url, props, clientInfo);
	}

	@Override
	public <R, V> R process(final long connuid, final long uid, final Command<R, V> cmd) throws SQLException, RemoteException {
		return processor.process(connuid, uid, cmd);
	}
}
