package com.bzsoft.wjdbc.command;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.SQLException;

import com.bzsoft.wjdbc.serial.StreamSerializer;

public class CallableStatementGetCharacterStreamCommand extends BaseCommand<char[], CallableStatement> {

	private static final long	serialVersionUID	= 3594832624574651235L;

	private int						index;
	private String					parameterName;

	public CallableStatementGetCharacterStreamCommand() {
		// empty
	}

	public CallableStatementGetCharacterStreamCommand(final int index) {
		this.index = index;
	}

	public CallableStatementGetCharacterStreamCommand(final String paramName) {
		parameterName = paramName;
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(index);
		if (parameterName == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			out.writeUTF(parameterName);
		}
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		index = in.readInt();
		final boolean isNotNull = in.readBoolean();
		if (isNotNull) {
			parameterName = in.readUTF();
		} else {
			parameterName = null;
		}
	}

	@Override
	public char[] execute(final CallableStatement cstmt, final ConnectionContext ctx) throws SQLException {
		Reader result;
		if (parameterName != null) {
			result = cstmt.getCharacterStream(parameterName);
		} else {
			result = cstmt.getCharacterStream(index);
		}
		try {
			// read reader and return as a char[]
			return StreamSerializer.toCharArray(result);
		} catch (final IOException ioe) {
			throw new SQLException(ioe);
		}
	}
}
