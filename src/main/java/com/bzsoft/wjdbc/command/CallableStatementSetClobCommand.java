package com.bzsoft.wjdbc.command;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.SQLException;

public class CallableStatementSetClobCommand extends BaseCommand<Void, CallableStatement> {
	private static final long	serialVersionUID	= 4264932633701227941L;

	private int						index;
	private String					parameterName;
	private Clob					clob;

	public CallableStatementSetClobCommand() {
		// empty
	}

	public CallableStatementSetClobCommand(final int index, final Clob clob) throws IOException {
		this.index = index;
		this.clob = clob;
	}

	public CallableStatementSetClobCommand(final String paramName, final Clob clob) throws IOException {
		parameterName = paramName;
		this.clob = clob;
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
		out.writeObject(clob);
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
		clob = (Clob) in.readObject();
	}

	@Override
	public Void execute(final CallableStatement cstmt, final ConnectionContext ctx) throws SQLException {
		if (parameterName != null) {
			cstmt.setClob(parameterName, clob);
		} else {
			cstmt.setClob(index, clob);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + (parameterName == null ? 0 : parameterName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CallableStatementSetClobCommand other = (CallableStatementSetClobCommand) obj;
		if (index != other.index) {
			return false;
		}
		if (parameterName == null) {
			if (other.parameterName != null) {
				return false;
			}
		} else if (!parameterName.equals(other.parameterName)) {
			return false;
		}
		return true;
	}


}
