	//code piece
	
	//这段提示了PropertyUtils的复制功能及范围
	//keyword 属性复制 PropertyUtils
	@Test
	public void testCopy() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Result rs = new Result();
		rs.setMessageType(Result.MESSAGE_TYPE_OK);
		
		BatchReturn br = new BatchReturn();
		
		PropertyUtils.copyProperties(br, rs);
		assertEquals(Result.MESSAGE_TYPE_OK, br.getMessageType());
		
		SubInstruction si = new SubInstruction ();
		SubInstructionPK id = new SubInstructionPK();
		SubInstructionRes sir = new SubInstructionRes();
		SubInstructionResPK sirpk = new SubInstructionResPK();
		si.setId(id);
		sir.setId(sirpk);
		id.setNoIndividualPackaging(11l);
		
		//Error id属性的类型不同
		//PropertyUtils.copyProperties(sir, si);
		//assertEquals(11l,sir.getId().getNoIndividualPackaging());		
	
		//OK
		SubInstruction si2 = new SubInstruction ();
		SubInstructionPK id2= new SubInstructionPK();
		si2.setId(id2);
		PropertyUtils.copyProperties(si2, si);
		assertEquals(11l,si2.getId().getNoIndividualPackaging());
	}